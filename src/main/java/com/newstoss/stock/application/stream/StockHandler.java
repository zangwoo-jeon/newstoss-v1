package com.newstoss.stock.application.stream;

import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockHandler implements KisApiMessageHandler{

    private final LoadStockPort loadStockPort;
    private final StockInfoPort stockInfoPort;

    @Override
    public boolean supports(String type) {
        return "stock".equals(type);
    }

    @Override
    public void handle(KisApiRequestDto dto) {
        Map<String, String> stockPayLoad = dto.getPayload();
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockPayLoad.get("stockCode"));
        Stock stock = loadStockPort.LoadStockByStockCode(stockPayLoad.get("stockCode"));
        stock.updateStockPrice(
                stockInfo.getPrice(), stockInfo.getChangeAmount(), stockInfo.getSign(), stockInfo.getChangeRate()
        );
        log.info("처리 완료 - stockCode: {}", stockPayLoad.get("stockCode"));
    }
}
