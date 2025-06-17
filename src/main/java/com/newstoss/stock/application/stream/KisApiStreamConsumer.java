package com.newstoss.stock.application.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.RedisStreamErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class KisApiStreamConsumer {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final LoadStockPort loadStockPort;
    private final StockInfoPort stockInfoPort;
    private final FxInfoPort fxInfoPort;

    private static final String STREAM = "kis-api-request";
    private static final String GROUP = "kis-group";
    private static final String CONSUMER = "worker-1";

    @Scheduled(fixedRate = 1000)
    public void consume() {
        List<MapRecord<String,Object,Object>> messages = redisTemplate
                .opsForStream()
                .read(Consumer.from(GROUP,CONSUMER),
                StreamReadOptions.empty().count(20),
                StreamOffset.create(STREAM, ReadOffset.lastConsumed()));
        for (MapRecord<String, Object, Object> message : messages) {
            KisApiRequestDto kisApiRequestDto = objectMapper.convertValue(message.getValue(), KisApiRequestDto.class);
            try {
                if ("stock".equals(kisApiRequestDto.getType())) {
                    KisStockDto stockInfo = stockInfoPort.getStockInfo(kisApiRequestDto.getStockCode());
                    Stock stock = loadStockPort.LoadStockByStockCode(kisApiRequestDto.getStockCode());
                    stock.updateStockPrice(
                            stockInfo.getPrice(),stockInfo.getChangeAmount(),stockInfo.getSign(),stockInfo.getChangeRate()
                    );
                } else if ("fx".equals(kisApiRequestDto.getType())) {
                    fxInfoPort.FxInfo(kisApiRequestDto.getFxType(), kisApiRequestDto.getFxCode());
                }

                redisTemplate.opsForStream().acknowledge(STREAM, GROUP, message.getId());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new CustomException(RedisStreamErrorCode.REDIS_CONSUMER_ERROR_CODE);
            }
        }
    }
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void reprocessPending() {
        PendingMessages pending = redisTemplate.opsForStream()
                .pending(STREAM, GROUP, Range.unbounded(), 100);

        for (PendingMessage msg : pending) {
            // 1. pending 메시지 id로 읽기 (read 메서드 사용)
            List<MapRecord<String, Object, Object>> records = redisTemplate.opsForStream().read(
                    StreamOffset.create(STREAM, ReadOffset.from(msg.getId()))
            );

            if (!records.isEmpty()) {
                MapRecord<String, Object, Object> record = records.get(0);

                // 2. 새 메시지로 재적재
                redisTemplate.opsForStream().add(STREAM, record.getValue());

                // 3. 원본 메시지 ACK
                redisTemplate.opsForStream().acknowledge(STREAM, GROUP, msg.getId());
            }
        }
    }
}
