package com.newstoss.stock.application.V2;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.stream.KisApiStreamProducer;
import com.newstoss.stock.adapter.inbound.dto.response.CategoryPageResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.CategoryStockResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.SearchResponseDto;
import com.newstoss.stock.application.port.in.GetCategoryUseCase;
import com.newstoss.stock.application.port.in.SearchStockUseCase;
import com.newstoss.stock.application.port.out.persistence.LoadCategoryPort;
import com.newstoss.stock.application.port.out.persistence.SearchStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("StockServiceV2")
public class StockRedisService implements GetCategoryUseCase,SearchStockUseCase {

    private final LoadCategoryPort loadCategoryPort;
    private final KisApiStreamProducer producer;
    private final SearchStockPort searchStockPort;
    private final ApplicationEventPublisher publisher;

    @Override
    public List<String> getCategories() {
        List<String> categoryAll = loadCategoryPort.getCategories();
        if (categoryAll.isEmpty()) {
            throw new CustomException(StockErrorCode.CATEGORY_NOT_FOUND);
        }
        return categoryAll;
    }

    @Override
    public CategoryPageResponseDto getStockByCategory(String category, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.ASC, "name");
        Page<Stock> stockPagingByCategory = loadCategoryPort.getStockByCategory(category,pageable);
        if (stockPagingByCategory.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        stockPagingByCategory.stream()
                .forEach(stock -> {
                    producer.sendStockRequest(stock.getStockCode());
                });
        List<CategoryStockResponseDto> list = stockPagingByCategory.stream()
                .map(stock -> {
                    return new CategoryStockResponseDto(
                            stock.getName(),
                            stock.getStockCode(),
                            stock.getPrice(),
                            stock.getChangeRate(),
                            stock.getSign(),
                            stock.getChangeAmount(),
                            stock.getStockImage()
                    );
                }).toList();
        return new CategoryPageResponseDto(
                stockPagingByCategory.getTotalPages(), list
        );
    }

    @Override
    public List<SearchResponseDto> searchStock(String query) {
        if (query != null) {
            query = query.toUpperCase();
        }
        List<Stock> stocks = searchStockPort.searchStock(query);
        if (query == null || query.isEmpty() || stocks.isEmpty()) {
            List<Stock> popularStocks = searchPopular();
            popularStocks.forEach(stock -> {
                        producer.sendStockRequest(stock.getStockCode());
                    });
            return popularStocks.stream()
                    .map(stock -> {
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stock.getPrice(),
                                stock.getChangeRate(),
                                stock.getSign(),
                                stock.getChangeAmount(),
                                stock.getStockImage()
                        );
                    }).toList();
        } else {
            stocks.forEach(stock -> {
                producer.sendStockRequest(stock.getStockCode());
            });
            return stocks.stream()
                    .map(stock -> {
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stock.getPrice(),
                                stock.getSign(),
                                stock.getChangeAmount(),
                                stock.getChangeRate(),
                                stock.getStockImage()
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
