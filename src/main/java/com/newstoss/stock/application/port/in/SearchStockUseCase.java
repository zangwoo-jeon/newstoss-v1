package com.newstoss.stock.application.port.in;

import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface SearchStockUseCase {
    List<Stock> searchStock(String query);
    List<Stock> searchPopular();
}
