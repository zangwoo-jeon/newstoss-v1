package com.newstoss.stock.application;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.outbound.kis.GetKisStockService;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.*;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockQueryService implements GetCategoryUseCase {

    private final StockRepository stockRepository;
    @Override
    public List<String> getCategories() {
        List<String> categoryAll = stockRepository.findCategoryAll();
        if (categoryAll.isEmpty()) {
            throw new CustomException(StockErrorCode.CATEGORY_NOT_FOUND);
        }
        return categoryAll;
    }

    public List<Stock> getStockByCategory(String category) {
        List<Stock> stockByCategory = stockRepository.findStockCodeByCategory(category);
        if (stockByCategory.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockByCategory;
    }

}
