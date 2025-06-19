package com.newstoss.stock.application.V1;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.inbound.dto.response.v1.CategoryPageResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.v1.CategoryStockResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.v1.SearchResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.in.v1.GetCategoryUseCase;
import com.newstoss.stock.application.port.in.v1.SearchStockUseCase;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import com.newstoss.stock.application.port.out.persistence.LoadCategoryPort;
import com.newstoss.stock.application.port.out.persistence.SearchStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
@Qualifier("StockQueryServiceV1")
public class StockQueryService implements GetCategoryUseCase, SearchStockUseCase {

    private final StockInfoPort stockInfoPort;
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
                    KisStockDto stockInfo = stockInfoPort.getStockInfo(stock.getStockCode());
                    return new CategoryStockResponseDto(
                            stock.getName(),
                            stock.getStockCode(),
                            stockInfo.getPrice(),
                            stockInfo.getChangeRate(),
                            stockInfo.getSign(),
                            stockInfo.getChangeAmount(),
                            stock.getStockImage()
                    );
                }).toList();
        return new CategoryPageResponseDto(
                stockPagingByCategory.getTotalPages(),
                list
        );
    }

    @Override
    public List<SearchResponseDto> searchStock(String keyword) {
        if (keyword != null) {
            keyword = keyword.toUpperCase();
        }
        List<Stock> stocks = searchStockPort.searchStock(keyword);
        if (keyword == null || keyword.isEmpty() || stocks.isEmpty()) {
            List<Stock> popularStocks = searchPopular();
            return popularStocks.stream()
                    .map(stock -> {
                        KisStockDto stockInfo = stockInfoPort.getStockInfo(stock.getStockCode());
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stockInfo.getPrice(),
                                stockInfo.getSign(),
                                stockInfo.getChangeAmount(),
                                stockInfo.getChangeRate(),
                                stock.getStockImage()
                        );
                    }).toList();
        } else {
            return stocks.stream()
                    .map(stock -> {
                        KisStockDto stockInfo = stockInfoPort.getStockInfo(stock.getStockCode());
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stockInfo.getPrice(),
                                stockInfo.getSign(),
                                stockInfo.getChangeAmount(),
                                stockInfo.getChangeRate(),
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
