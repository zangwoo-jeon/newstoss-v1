package com.newstoss.news.adapter.out.redis.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.sse.ChatStreamEmitters;
import com.newstoss.news.adapter.in.web.sse.dto.ChatStreamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ChatRedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ChatStreamEmitters emitters;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String rawMessage = new String(message.getBody());
            System.out.println("📩 Redis 수신 원본: " + rawMessage);

            ChatStreamResponse response = objectMapper.readValue(rawMessage, ChatStreamResponse.class);

            if (response.clientId() == null || response.message() == null) {
                System.err.println("❌ Redis 메시지에 필수값 누락: " + rawMessage);
                return;
            }

            UUID clientId;
            try {
                clientId = UUID.fromString(response.clientId());
            } catch (IllegalArgumentException e) {
                System.err.println("❌ 잘못된 UUID 형식: " + response.clientId());
                return;
            }

            emitters.get(clientId).ifPresentOrElse(emitter -> {
                try {
                    emitter.send(SseEmitter.event().name("chat").data(response.message()));
                    if (response.isLast()) {
                        emitter.send(SseEmitter.event().name("chat-end").data("[DONE]"));
                        emitter.complete();
                        emitters.remove(clientId);
                    }
                } catch (IOException e) {
                    emitter.completeWithError(e);
                    emitters.remove(clientId);
                    System.err.println("❌ SSE 전송 실패: " + e.getMessage());
                }
            }, () -> {
                System.err.println("⚠️ emitter 없음. UUID: " + clientId);
            });

        } catch (Exception e) {
            System.err.println("❌ Redis 메시지 처리 실패: " + e.getMessage());
            System.err.println("⚠️ 메시지 내용: " + new String(message.getBody()));
        }
    }
}
