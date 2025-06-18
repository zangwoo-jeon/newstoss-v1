package com.newstoss.stock.application.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.RedisStreamErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
@SuppressWarnings("unchecked")
public class KisApiStreamConsumer {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final FxInfoPort fxInfoPort;
    private final List<KisApiMessageHandler> handlers;

    private static final String STREAM = "kis-api-request";
    private static final String GROUP = "kis-group";
    private static final String CONSUMER = "worker-1";

//    @Scheduled(fixedRate = 1000)
    public void consume() {
        log.info("[consume] 컨슈머 동작");

        List<MapRecord<String, Object, Object>> messages = redisTemplate
                .opsForStream()
                .read(Consumer.from(GROUP, CONSUMER),
                        StreamReadOptions.empty().block(Duration.ofSeconds(1)).count(20),
                        StreamOffset.create(STREAM, ReadOffset.lastConsumed()));
        if (messages != null) {
            log.info("message 개수: {}" , messages.size());
            for (MapRecord<String, Object, Object> message : messages) {
                processMessage(message);
            }
        }

        // ✅ 메시지 처리 후에도 pending이 남아있다면 즉시 재시도
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
                    processMessage(claimed.get(0));
                } else {
                    log.warn("claim 했지만 메시지 없음: {}", messageId);
                }
            }
        }
    }

    private void processMessage(MapRecord<String, Object, Object> record) {
        try {
            KisApiRequestDto dto = objectMapper.convertValue(record.getValue(), KisApiRequestDto.class);
            log.info("메세지 처리중 : {}" ,dto.getPayload().get("stockCode"));
            KisApiMessageHandler handler = handlers.stream()
                    .filter(h -> h.supports(dto.getType()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(RedisStreamErrorCode.REDIS_TYPE_ERROR_CODE));
            handler.handle(dto);

            // ✅ ACK 처리
            redisTemplate.opsForStream().acknowledge(STREAM, GROUP, record.getId());

        } catch (Exception e) {
            log.error("처리 실패 - id: {}, 에러: {}", record.getId(), e.getMessage(), e);

            // 선택사항: 실패 시 dead-letter로 보내기
            // redisTemplate.opsForStream().add("kis-dead-letter", record.getValue());
            // redisTemplate.opsForStream().acknowledge(STREAM, GROUP, record.getId());
        }
    }

//    @Scheduled(fixedRate = 60_000) // 1분마다
    public void trimStream() {
        Long trimmed = redisTemplate.opsForStream().trim(STREAM, 1000); // 정확하게 1000개 유지
        log.info("Stream 트리밍: {}개 제거됨", trimmed);
    }
}
