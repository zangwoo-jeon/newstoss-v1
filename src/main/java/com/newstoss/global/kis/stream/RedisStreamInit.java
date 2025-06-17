package com.newstoss.global.kis.stream;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisStreamInit {

    private final RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void initConsumerGroup() {
        if (!redisTemplate.hasKey("kis-api-request")) {
            log.info("KIS stream consumer group 초기화");
            redisTemplate.opsForStream().add("kis-api-request", Map.of("init", "true"));
        }

        try {
            redisTemplate.opsForStream()
                    .createGroup("kis-api-request", ReadOffset.latest(), "kis-group");
        } catch (RedisSystemException e) {
            if (e.getRootCause() instanceof io.lettuce.core.RedisBusyException) {
                log.info("Consumer group already exists.");
            } else {
                throw e;
            }
        }
    }
}
