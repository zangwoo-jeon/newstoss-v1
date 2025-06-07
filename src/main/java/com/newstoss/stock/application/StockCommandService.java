package com.newstoss.stock.application;

import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.CreateStockUseCase;
import com.newstoss.stock.application.port.in.UpdateStockSearchCount;
import com.newstoss.stock.application.port.out.persistence.CreateStockPort;
import com.newstoss.stock.application.port.out.persistence.StockSearchCounterPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockCommandService implements CreateStockUseCase , UpdateStockSearchCount {

    private final CreateStockPort createStockPort;
    private final StockSearchCounterPort stockSearchCounterPort;
    /**
     * 주식 저장
     * @param stock
     * @return stockId
     */
    @Override
    public Long save(Stock stock) {
        return createStockPort.create(stock);
    }

    @Override
    public void StockSearchCounter(String stockCode) {
        stockSearchCounterPort.increase(stockCode);
    }
}
