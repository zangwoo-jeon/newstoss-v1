package com.newstoss.stock.application.V2;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.inbound.dto.response.v2.CategoryPageDto;
import com.newstoss.stock.adapter.inbound.dto.response.v2.StockSimpleDto;
import com.newstoss.stock.application.port.in.v2.GetCategorySimpleUseCase;
import com.newstoss.stock.application.port.in.v2.SearchStockUseCaseV2;
import com.newstoss.stock.application.port.out.persistence.LoadCategoryPort;
import com.newstoss.stock.application.port.out.persistence.SearchStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class StockRedisService implements GetCategorySimpleUseCase, SearchStockUseCaseV2 {

    private final LoadCategoryPort loadCategoryPort;
    private final SearchStockPort searchStockPort;

    @Override
    public List<StockSimpleDto> searchStock(String query) {
        if (query != null) {
            query = query.toUpperCase();
        }
        List<Stock> stocks = searchStockPort.searchStock(query);
        if (query == null || query.isEmpty() || stocks.isEmpty()) {
            List<Stock> popularStocks = searchPopular();
            return popularStocks.stream()
                    .map(stock -> new StockSimpleDto(
                            stock.getName(),
                            stock.getStockCode()
                    )).toList();
        } else {
            return stocks.stream()
                    .map(stock -> new StockSimpleDto(
                            stock.getName(),
                            stock.getStockCode()
                    )).toList();
        }
    }

    public List<Stock> searchPopular() {
        List<Stock> stocks = searchStockPort.searchPopuarStock();
        if (stocks.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stocks;
    }

    @Override
    public CategoryPageDto getCategorySimple(String category, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.ASC, "name");
        Page<Stock> stockPagingByCategory = loadCategoryPort.getStockByCategory(category,pageable);
        if (stockPagingByCategory.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        List<StockSimpleDto> list = stockPagingByCategory.stream()
                .map(stock -> new StockSimpleDto(stock.getName(), stock.getStockCode()))
                .toList();
        return new CategoryPageDto(stockPagingByCategory.getTotalPages(), list);
    }
}
