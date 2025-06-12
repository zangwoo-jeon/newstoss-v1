package com.newstoss.news.adapter.out.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;
import com.newstoss.news.application.redis.port.out.HighlightNewsCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;


@Component
@RequiredArgsConstructor
public class HighlightNewsCacheImpl implements HighlightNewsCachePort {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String KEY = "highlight-with-related";
    private static final Duration TTL = Duration.ofMinutes(2);

    @Override
    public void saveHighlightsWithRelated(List<NewsMathRelatedDTO> data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(KEY, json, TTL);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("하이라이트+관련뉴스 캐시 저장 실패", e);
        }
    }

    @Override
    public List<NewsMathRelatedDTO> loadHighlightsWithRelated() {
        String json = redisTemplate.opsForValue().get(KEY);

        if (json == null) {
            System.out.println("⚠️ Redis에 저장된 highlight-with-related 캐시 없음");
            return List.of();
        }

        try {
            return objectMapper.readValue(
                    json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, NewsMathRelatedDTO.class)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("하이라이트+관련뉴스 캐시 역직렬화 실패", e);
        }
    }

    @Override
    public void saveHighlightsWithRelatedTest(List<NewsMathRelatedDTOTest> highlightData) {
        try {
            String json = objectMapper.writeValueAsString(highlightData);
            redisTemplate.opsForValue().set(KEY, json, TTL);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("하이라이트+관련뉴스 캐시 저장 실패", e);
        }
    }

    @Override
    public List<NewsMathRelatedDTOTest> loadHighlightsWithRelatedTest() {
        String json = redisTemplate.opsForValue().get(KEY);

        if (json == null) {
            System.out.println("⚠️ Redis에 저장된 highlight-with-related 캐시 없음");
            return List.of();
        }

        try {
            return objectMapper.readValue(
                    json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, NewsMathRelatedDTOTest.class)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("하이라이트+관련뉴스 캐시 역직렬화 실패", e);
        }
    }

    @Override
    public boolean trySetInitCacheLock(Duration ttl) {
        // 락 키는 별도로 관리 (KEY + "-lock" 형태)
        String lockKey = KEY + "-lock";
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", ttl)
        );
    }
}
