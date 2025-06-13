package com.newstoss.stock.adapter.outbound.persistence;

import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.out.persistence.CreateStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaCommandRepositoryAdapter implements CreateStockPort {

    private final StockRepository repository;

    @Override
    public Long create(Stock stock) {

        Stock save = repository.save(stock);
        return save.getId();
    }

}
