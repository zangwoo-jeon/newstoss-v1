package com.newstoss.stock.adapter.outbound.persistence;

import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.out.persistence.LoadCategoryPort;
import com.newstoss.stock.application.port.out.persistence.SearchStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaQueryRepositoryAdapter implements LoadCategoryPort , SearchStockPort {

    private final StockRepository repository;


    @Override
    public List<String> getCategories() {
        return repository.findCategoryAll();
    }

    @Override
    public Page<Stock> getStockByCategory(String category, Pageable pageable) {
        return repository.findStockCodeByCategory(category, pageable);
    }

    @Override
    public List<Stock> searchStock(String keyword) {
        return repository.searchStock(keyword);
    }

    @Override
    public List<Stock> searchPopuarStock() {
        return repository.findStockByStockSearchCount();
    }
}
