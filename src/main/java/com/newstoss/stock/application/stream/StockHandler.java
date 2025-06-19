package com.newstoss.stock.application.stream;

import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
import com.newstoss.stock.application.sse.EmitterRepository;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockHandler implements KisApiMessageHandler{

    private final StockInfoPort stockInfoPort;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmitterRepository emitterRepository;
    @Override
    public boolean supports(String type) {
        return "stock".equals(type);
    }

    @Override
    public void handle(KisApiRequestDto dto) {
        Map<String, String> stockPayLoad = dto.getPayload();
        String stockCode = stockPayLoad.get("stockCode");
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);

        redisTemplate.opsForValue().set("stock:" + stockCode, stockInfo);
        Map<String, SseEmitter> allEmitters = emitterRepository.findAllEmitter();
        allEmitters.forEach((emitterId, emitter) -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("stock")
                                .data(stockInfo)
                                .id(stockCode)
                );
            } catch (Exception e) {
                log.warn("SSE 전송 실패 → emitterId: {}", emitterId, e);
                emitterRepository.deleteById(emitterId); // 실패한 emitter 정리
            }
        });
        log.info("처리 완료 - stockCode: {}", stockCode);
    }
}
