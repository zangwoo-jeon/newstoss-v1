package com.newstoss.stock.application.V2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.kis.dto.KisApiRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisMessageRecoveryTask {

    private final RedisTemplate<String, Object> redisTemplate;
    private final KisStreamListener listener;
    private final ObjectMapper objectMapper;

    private static final String STREAM = "kis-api-request";
    private static final String GROUP = "kis-group";
    private static final String CONSUMER = "recovery-1";

    @Scheduled(fixedDelay = 1_000) // 10초마다 체크
    public void recoverPendingMessages() {
        PendingMessages pending = redisTemplate.opsForStream()
                .pending(STREAM, GROUP, Range.unbounded(), 10);

        if (!pending.isEmpty()) {
            log.warn("[consume] pending 메시지 {}개 발견 → 즉시 재처리", pending.size());
            for (PendingMessage pendingMessage : pending) {
                String messageId = pendingMessage.getId().getValue();

                List<MapRecord<String, Object, Object>> claimed = redisTemplate.opsForStream().claim(
                        STREAM,
                        GROUP,
                        CONSUMER,
                        Duration.ofMillis(0),
                        RecordId.of(messageId)
                );

                if (!claimed.isEmpty()) {
                    listener.onMessage(claimed.get(0));
                } else {
                    log.warn("claim 했지만 메시지 없음: {}", messageId);
                }
            }
        }
    }
}

