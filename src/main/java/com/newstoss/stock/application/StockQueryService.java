package com.newstoss.stock.application;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.KisTokenManager;
import com.newstoss.global.kis.KisTokenProperties;
import com.newstoss.stock.adapter.outbound.kis.dto.KisHTS20Stock;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.*;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockQueryService implements GetCategoryUseCase , SearchStockUseCase{

    private final KisTokenManager kisTokenManager;
    private final KisTokenProperties kisTokenProperties;
    private final RestTemplate restTemplate;
    private final GetStockInfoUseCase getStockInfoUseCase;
    private final StockRepository stockRepository;
    @Override
    public List<String> getCategories() {
        List<String> categoryAll = stockRepository.findCategoryAll();
        if (categoryAll.isEmpty()) {
            throw new CustomException(StockErrorCode.CATEGORY_NOT_FOUND);
        }
        return categoryAll;
    }

    public Page<Stock> getStockByCategory(String category , int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.ASC, "name");
        Page<Stock> stockPagingByCategory = stockRepository.findStockCodeByCategory(category,pageable);
        if (stockPagingByCategory.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockPagingByCategory;
    }

    @Override
    public List<Stock> searchStock(String query) {
        List<Stock> stocks = stockRepository.searchStock(query);
        if (stocks.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stocks;
    }

    @Override
    public List<Stock> searchPopular() {
        List<Stock> stocks = stockRepository.findStockByStockSearchCount();
        if (stocks.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stocks;
    }
}
