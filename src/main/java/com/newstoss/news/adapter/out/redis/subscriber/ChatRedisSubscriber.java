package com.newstoss.news.adapter.out.redis.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.sse.emitter.ChatStreamEmitters;
import com.newstoss.news.adapter.in.web.sse.dto.ChatStreamResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
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
    private final ExecutorService clientDispatchExecutor = Executors.newFixedThreadPool(100);

    @PostConstruct
    public void initDispatcher() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::dispatchMessages, 0, 10, TimeUnit.MILLISECONDS);

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::sendPingToActiveWriters, 0, 1, TimeUnit.SECONDS);

    }

    private void sendPingToActiveWriters() {
        Set<UUID> writerIds = emitters.getWriterMap().keySet(); // ChatStreamEmitters 내부에서 writerMap 접근 제공 필요

        for (UUID clientId : writerIds) {
            emitters.getWriter(clientId).ifPresent(writer -> {
                try {
                    writer.write("event: ping\n");
                    writer.write("data: {}\n\n");
                    writer.flush();
                    log.debug("📡 Ping 전송: {}", clientId);
                } catch (Exception e) {
                    log.warn("⚠️ Ping 전송 실패 → 연결 제거: {}", clientId);
                    emitters.removeWriter(clientId);
                    cleanup(clientId);
                }
            });
        }
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String rawMessage = new String(message.getBody());
        try {
            ChatStreamResponse response = objectMapper.readValue(rawMessage, ChatStreamResponse.class);

            if (response.getIndex() == 0) {
                log.info("📥 [1️⃣ Redis 수신] clientId={} index=0 시간 = {}", response.getClientId(), System.currentTimeMillis());
            }


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
//        log.debug("📦 [버퍼 상태] clientId={} expectedIndex={} bufferKeys={}", clientId, expectedIndex, bufferKeysSnapshot);

        while (buffer.containsKey(expectedIndex)) {
            ChatStreamResponse msg = buffer.remove(expectedIndex);
//            if (emitters.get(clientId).isPresent()) {
//                send(clientId, msg, false); // SseEmitter 방식
//                log.info("✅ SSE Emitter 메시지 전송: {}", msg);
//            }
            if (emitters.getWriter(clientId).isPresent()) {
                sendByWriter(clientId, msg, false); // ✨ Writer 방식 추가
//                log.info("✅ Writer SSE 메시지 전송: {}", msg);
            }
            lastSentIndex.put(clientId, expectedIndex);
            expectedIndex++;
        }

        List<Integer> toRemove = new ArrayList<>();
        for (Map.Entry<Integer, ChatStreamResponse> entry : buffer.entrySet()) {
            if (entry.getKey() < lastSentIndex.get(clientId)) {
                ChatStreamResponse delayedMsg = entry.getValue();
                if (delayedMsg == null) continue;

                
//                if (emitters.get(clientId).isPresent()) {
//                    send(clientId, delayedMsg, true); // emitter 방식
//                    log.info("✅ SSE 지연 메시지 전송 (Emitter): {}", delayedMsg);
//                }
                if (emitters.getWriter(clientId).isPresent()) {
                    sendByWriter(clientId, delayedMsg, true); // writer 방식
//                    log.info("✅ SSE 지연 메시지 전송 (Writer): {}", delayedMsg);
                }

                toRemove.add(entry.getKey());
            }
        }
        for (Integer key : toRemove) {
            buffer.remove(key);
        }
    }

    public void dispatchForClientImmediately(UUID clientId) {
        clientDispatchExecutor.submit(() -> dispatchForClient(clientId));
    }

    private void sendByWriter(UUID clientId, ChatStreamResponse response, boolean late) {
        emitters.getWriter(clientId).ifPresent(writer -> {
            try {
                if (response.getIndex() == 0) {
                    log.info("✅ [3️⃣ Writer 첫 메세지 전송 완료] clientId={}, index=0, time={}", clientId, System.currentTimeMillis());
                }
                String jsonData = objectMapper.writeValueAsString(response.getContent());
//                log.info("raw ml response : {}",response.getContent());
//                log.info("json ml response : {}",jsonData);
                writer.write("event: chat\n");
                writer.write("data: " + jsonData + "\n\n");
                writer.flush();
//                log.info("🖋️ Writer 메시지 전송: {}", response.getContent());

                if (writer.checkError()) {
                    log.warn("❌ Writer 상태 오류 발생 → 마지막 메시지 전송 못함: clientId={}", clientId);
                    emitters.removeWriter(clientId);
                    cleanup(clientId);
                    return;
                }

                if (response.isLast()) {
                    try {
                        writer.write("event: chat\n");
                        writer.write("data: \"[DONE]\"\n\n");
                        writer.flush();
                        log.info("✅ [3️⃣ Writer 전송 완료] clientId={}, index={}, time={}", clientId, response.getIndex(), System.currentTimeMillis());
//                        log.info("✅ [DONE] 전송 완료: {}", clientId);
                    } catch (Exception e) {
                        log.warn("❌ [DONE] 전송 실패: {}", e.getMessage());
                    } finally {
                        Thread.sleep(100); // 혹시 flush를 기다리는 시간
                        emitters.removeWriter(clientId);
                        cleanup(clientId);
                    }
                }
            } catch (Exception e) {
                log.warn("❌ Writer 전송 실패: {}", e.getMessage());
                emitters.removeWriter(clientId);
                cleanup(clientId);
            }
        });
    }
    private void cleanup(UUID clientId) {
//        emitters.remove(clientId);
        emitters.removeWriter(clientId);
        pendingBuffer.remove(clientId);
        indexTimestamps.remove(clientId);
        lastSentIndex.remove(clientId);
    }

//    private void send(UUID clientId, ChatStreamResponse response, boolean late) {
//        emitters.get(clientId).ifPresentOrElse(emitter -> {
//            try {
//                emitter.send(SseEmitter.event()
//                        .name("chat")
//                        .data(response.getContent())
//                );
//                emitter.send(SseEmitter.event()
//                        .name("dummy")
//                        .data(UUID.randomUUID().toString())
//                );
////                emitter.send(SseEmitter.event().comment(""));
//
//                log.info("✅ SSE 메시지 전송: {}", response.getContent());
//
//                if (response.isLast()) {
//                    emitter.send(SseEmitter.event().name("chat-end").data("[DONE]"));
//                    emitter.complete();
//                    emitters.remove(clientId);
//
//                    pendingBuffer.remove(clientId);
//                    indexTimestamps.remove(clientId);
//                    lastSentIndex.remove(clientId);
//                }
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//                emitters.remove(clientId);
//                log.warn("❌ SSE 전송 실패: {}", e.getMessage());
//            }
//        }, () -> log.warn("⚠️ emitter 없음: {}", clientId));
//    }
    //--------------------------------------------------------------------
}