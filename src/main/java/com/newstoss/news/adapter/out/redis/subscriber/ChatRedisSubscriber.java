package com.newstoss.news.adapter.out.redis.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.sse.emitter.ChatStreamEmitters;
import com.newstoss.news.adapter.in.web.sse.dto.ChatStreamResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatRedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ChatStreamEmitters emitters;

    private final Map<UUID, ConcurrentSkipListMap<Integer, ChatStreamResponse>> pendingBuffer = new ConcurrentHashMap<>();
    private final Map<UUID, Map<Integer, Long>> indexTimestamps = new ConcurrentHashMap<>();
    private final Map<UUID, Integer> lastSentIndex = new ConcurrentHashMap<>();

    private static final long TIMEOUT_MS = 1000; // 1초

    // 사용자별 전송 병렬화를 위한 스레드풀
    private final ExecutorService clientDispatchExecutor = Executors.newFixedThreadPool(10);

    @PostConstruct
    public void initDispatcher() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::dispatchMessages, 0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String rawMessage = new String(message.getBody());

        try {
            ChatStreamResponse response = objectMapper.readValue(rawMessage, ChatStreamResponse.class);

            if (response.getClientId() == null || response.getContent() == null || response.getIndex() == null) {
                log.warn("❌ 필수값 누락: {}", rawMessage);
                return;
            }

            UUID clientId = UUID.fromString(response.getClientId());
            int index = response.getIndex();

            pendingBuffer
                    .computeIfAbsent(clientId, id -> new ConcurrentSkipListMap<>())
                    .put(index, response);

            indexTimestamps
                    .computeIfAbsent(clientId, id -> new ConcurrentHashMap<>())
                    .put(index, System.currentTimeMillis());

        } catch (Exception e) {
            log.error("❌ Redis 메시지 처리 실패: {}", e.getMessage());
            log.warn("⚠️ 메시지 내용: {}", rawMessage);
        }
    }

    private void dispatchMessages() {
        for (UUID clientId : pendingBuffer.keySet()) {
            clientDispatchExecutor.submit(() -> dispatchForClient(clientId));
        }
    }

    private void dispatchForClient(UUID clientId) {
        ConcurrentSkipListMap<Integer, ChatStreamResponse> buffer = pendingBuffer.get(clientId);
        if (buffer == null) return;

        int expectedIndex = lastSentIndex.getOrDefault(clientId, -1) + 1;

        Set<Integer> bufferKeysSnapshot = new TreeSet<>(buffer.keySet());
        log.debug("📦 [버퍼 상태] clientId={} expectedIndex={} bufferKeys={}", clientId, expectedIndex, bufferKeysSnapshot);

        while (buffer.containsKey(expectedIndex)) {
            ChatStreamResponse msg = buffer.remove(expectedIndex);
            send(clientId, msg, false);
            log.info("✅ SSE 메시지 전송: {}", msg);
            lastSentIndex.put(clientId, expectedIndex);
            expectedIndex++;
        }

        List<Integer> toRemove = new ArrayList<>();
        for (Map.Entry<Integer, ChatStreamResponse> entry : buffer.entrySet()) {
            if (entry.getKey() < lastSentIndex.get(clientId)) {
                send(clientId, entry.getValue(), true);
                log.info("✅ SSE 지연 메시지 전송: {}", entry.getValue());
                toRemove.add(entry.getKey());
            }
        }
        toRemove.forEach(buffer::remove);
    }

    private void send(UUID clientId, ChatStreamResponse response, boolean late) {
        emitters.get(clientId).ifPresentOrElse(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("chat")
                        .data(response.getContent())
                );
                emitter.send(SseEmitter.event().comment("flush"));

                log.info("✅ SSE 메시지 전송: {}", response.getContent());

                if (response.isLast()) {
                    emitter.send(SseEmitter.event().name("chat").data("[DONE]"));
                    emitter.complete();
                    emitters.remove(clientId);

                    pendingBuffer.remove(clientId);
                    indexTimestamps.remove(clientId);
                    lastSentIndex.remove(clientId);
                }
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(clientId);
                log.warn("❌ SSE 전송 실패: {}", e.getMessage());
            }
        }, () -> log.warn("⚠️ emitter 없음: {}", clientId));
    }
}
