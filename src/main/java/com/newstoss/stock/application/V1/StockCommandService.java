package com.newstoss.stock.application.V1;

import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.in.CreateStockUseCase;
import com.newstoss.stock.application.port.in.UpdateStockSearchCount;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import com.newstoss.stock.application.port.out.persistence.CreateStockPort;
import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockCommandService implements CreateStockUseCase , UpdateStockSearchCount {

    private final CreateStockPort createStockPort;
    private final StockInfoPort stockInfoPort;
    private final LoadStockPort loadStockPort;
    /**
     * 주식 저장
     * @param stockCode 주식 코드
     * @return stockId
     */
    @Override
    public Long save(String stockCode) {
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);
        String name = stockInfoPort.getStockName(stockCode);
        Stock stock = Stock.createStock(stockCode, name, null, stockInfo.getMarketName(), stockInfo.getCategoryName());
        return createStockPort.create(stock);
    }

    @Override
    public void StockSearchCounter(String stockCode) {
        Stock stock = loadStockPort.LoadStockByStockCode(stockCode);
        stock.incrementStockSearchCount();
    }
}
