package com.newstoss.stock.application.V2;

import com.newstoss.stock.adapter.outbound.redis.KisApiStreamProducer;
import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
import com.newstoss.stock.application.sse.EmitterRepository;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisStockInfoService{

    private final KisApiStreamProducer producer;
    private final EmitterRepository emitterRepository;
    private final LoadStockPort loadStockPort;

    @Scheduled(fixedDelay = 30_000)
    public void enqueueStockCodesIfClientsConnected() {
        if (emitterRepository.findAllEmitter().isEmpty()) {
            log.info("No emitters found");
            return;
        }

        List<Stock> stocks = loadStockPort.LoadAllStocks();

        stocks.forEach(stock -> {
            producer.sendStockRequest(stock.getStockCode());
        });
    }
}
