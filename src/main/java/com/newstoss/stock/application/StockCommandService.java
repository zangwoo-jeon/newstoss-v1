package com.newstoss.stock.application;

import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.CreateStockUseCase;
import com.newstoss.stock.application.port.in.UpdateStockUseCase;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockCommandService implements CreateStockUseCase , UpdateStockUseCase {

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

    /**
     * 주식 가격 업데이트
     * @param stockId
     * @param price
     * @return stockId
     */
    @Override
    public Long updatePrice(Long stockId, Integer price) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));
        stock.updatePrice(price);
        return stock.getId();
    }
}
