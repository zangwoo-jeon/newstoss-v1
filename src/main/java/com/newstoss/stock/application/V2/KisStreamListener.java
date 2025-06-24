package com.newstoss.stock.application.V2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.RedisStreamErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.application.stream.KisApiMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
@SuppressWarnings("unchecked")
public class KisStreamListener implements StreamListener<String, MapRecord<String, Object, Object>> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final List<KisApiMessageHandler> handlers;
    private final AtomicInteger counter = new AtomicInteger();

    private static final String STREAM = "kis-api-request";
    private static final String GROUP = "kis-group";


    @Override
    public void onMessage(MapRecord<String, Object, Object> message) {
        try {
            KisApiRequestDto dto = objectMapper.convertValue(message.getValue(), KisApiRequestDto.class);

            KisApiMessageHandler handler = handlers.stream()
                    .filter(h -> h.supports(dto.getType()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(RedisStreamErrorCode.REDIS_TYPE_ERROR_CODE));

            handler.handle(dto);

            redisTemplate.opsForStream().acknowledge(STREAM, GROUP, message.getId());
            int count = counter.incrementAndGet();
//            log.info("메세지 처리 횟수 : {}", count);
            //처리후 삭제
            redisTemplate.opsForStream().delete(STREAM, message.getId());
        } catch (Exception e) {
            log.error("처리 실패 - id: {}, 에러: {}", message.getId(), e.getMessage(), e);
            // 필요 시 dead-letter로 보낼 수 있음
        }
    }
}
