package com.newstoss.global.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.sse.SseEmitters;
import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisNewsSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final SseEmitters sseEmitters;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            MLNewsDTO news = objectMapper.readValue(json, MLNewsDTO.class);
            sseEmitters.send(news);
        } catch (Exception e) {
            log.error("Redis 수신 중 오류", e);
        }
    }
}

