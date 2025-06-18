package com.newstoss.stock.application.V2;

import com.newstoss.global.kis.stream.KisApiStreamProducer;
import com.newstoss.stock.application.port.in.GetStockInfoUseCase;
import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("StockInfoServiceV2")
public class RedisStockInfoService implements GetStockInfoUseCase {

    private final KisApiStreamProducer producer;
    private final LoadStockPort loadStockPort;


    @Override
    public String getStockPrice(String stockCode) {
        producer.sendStockRequest(stockCode);
        Stock stock = loadStockPort.LoadStockByStockCode(stockCode);
        return stock.getPrice();
    }
}
