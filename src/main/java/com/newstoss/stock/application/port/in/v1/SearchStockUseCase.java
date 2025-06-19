package com.newstoss.stock.application.port.in.v1;

import com.newstoss.stock.adapter.inbound.dto.response.v1.SearchResponseDto;
import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface SearchStockUseCase {
    List<SearchResponseDto> searchStock(String query);
    List<Stock> searchPopular();
}
