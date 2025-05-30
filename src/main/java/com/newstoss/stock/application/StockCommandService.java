package com.newstoss.stock.application;

import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.CreateStockUseCase;
import com.newstoss.stock.application.port.in.UpdateStockSearchCount;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockCommandService implements CreateStockUseCase , UpdateStockSearchCount {

    private final StockRepository stockRepository;

    /**
     * 주식 저장
     * @param stock
     * @return stockId
     */
    @Override
    public Long save(Stock stock) {
        Stock savedStock =stockRepository.save(stock);
        return savedStock.getId();
    }

    @Override
    public void StockSearchCounter(String stockCode) {
        stockRepository.findByStockCode(stockCode)
                .ifPresent(Stock::incrementStockSearchCount);
    }
}
