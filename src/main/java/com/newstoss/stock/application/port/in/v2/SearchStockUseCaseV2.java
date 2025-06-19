package com.newstoss.stock.application.port.in.v2;

import com.newstoss.stock.adapter.inbound.dto.response.v2.StockSimpleDto;

import java.util.List;

public interface SearchStockUseCaseV2 {
    List<StockSimpleDto> searchStock(String query);
}
