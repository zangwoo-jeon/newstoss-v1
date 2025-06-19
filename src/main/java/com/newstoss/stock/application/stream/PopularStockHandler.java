package com.newstoss.stock.application.stream;

import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPopularDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.PopularStockPort;
import com.newstoss.stock.application.sse.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PopularStockHandler implements KisApiMessageHandler {

    private final PopularStockPort popularStockPort;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmitterRepository emitterRepository;

    @Override
    public boolean supports(String type) {
        return "popular".equals(type);
    }

    @Override
    public void handle(KisApiRequestDto dto) {
        List<KisPopularDto> popularStocks = popularStockPort.getPopularStock();
        List<KisPopularDto> list = popularStocks.stream()
                .sorted(Comparator.comparing(Stock -> Integer.parseInt(Stock.getRank())))
                .limit(6)
                .toList();
        Map<String, SseEmitter> allEmitters = emitterRepository.findAllEmitter();
        allEmitters.forEach((emitterId, emitter) -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("popular")
                                .data(list)
                                .id("popularStocks")
                );
            } catch (Exception e) {
                log.warn("SSE 전송 실패 → emitterId: {}", emitterId, e);
                emitterRepository.deleteById(emitterId); // 실패한 emitter 정리
            }
        });
        log.info("처리 완료 - popular");

    }


}
