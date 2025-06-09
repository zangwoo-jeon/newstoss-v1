package com.newstoss.stock.application;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.inbound.dto.response.CategoryPageResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.CategoryStockResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.SearchResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.in.*;
import com.newstoss.stock.application.port.out.kis.KisStockInfoPort;
import com.newstoss.stock.application.port.out.persistence.LoadCategoryPort;
import com.newstoss.stock.application.port.out.persistence.SearchStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockQueryService implements GetCategoryUseCase , SearchStockUseCase{

    private final KisStockInfoPort kisStockInfoPort;
    private final LoadCategoryPort loadCategoryPort;
    private final SearchStockPort searchStockPort;
    @Override
    public List<String> getCategories() {
        List<String> categoryAll = loadCategoryPort.getCategories();
        if (categoryAll.isEmpty()) {
            throw new CustomException(StockErrorCode.CATEGORY_NOT_FOUND);
        }
        return categoryAll;
    }

    public CategoryPageResponseDto getStockByCategory(String category , int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.ASC, "name");
        Page<Stock> stockPagingByCategory = loadCategoryPort.getStockByCategory(category,pageable);
        if (stockPagingByCategory.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        List<CategoryStockResponseDto> list = stockPagingByCategory.stream()
                .map(stock -> {
                    KisStockDto stockInfo = kisStockInfoPort.getStockInfo(stock.getStockCode());
                    return new CategoryStockResponseDto(
                            stock.getName(),
                            stock.getStockCode(),
                            stockInfo.getPrice(),
                            stockInfo.getChangeRate(),
                            stockInfo.getSign(),
                            stockInfo.getChangeAmount()
                    );
                }).toList();
        return new CategoryPageResponseDto(
                stockPagingByCategory.getTotalPages(),
                list
        );
    }

    @Override
    public List<SearchResponseDto> searchStock(String keyword) {
        List<Stock> stocks = searchStockPort.searchStock(keyword);
        if (keyword == null || keyword.isEmpty() || stocks.isEmpty()) {
            List<Stock> popularStocks = searchPopular();
            return popularStocks.stream()
                    .map(stock -> {
                        KisStockDto stockInfo = kisStockInfoPort.getStockInfo(stock.getStockCode());
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stockInfo.getPrice(),
                                stockInfo.getSign(),
                                stockInfo.getChangeAmount(),
                                stockInfo.getChangeRate()
                        );
                    }).toList();
        } else {
            return stocks.stream()
                    .map(stock -> {
                        KisStockDto stockInfo = kisStockInfoPort.getStockInfo(stock.getStockCode());
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stockInfo.getPrice(),
                                stockInfo.getSign(),
                                stockInfo.getChangeAmount(),
                                stockInfo.getChangeRate()
                        );
                    }).toList();
        }
    }

    @Override
    public List<Stock> searchPopular() {
        List<Stock> stocks = searchStockPort.searchPopuarStock();
        if (stocks.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stocks;
    }
}
