package com.newstoss.stock.application.port.out.persistence;

import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface LoadStockPort {
    Stock LoadStockByStockCode(String stockCode);
    List<Stock> LoadAllStocks();
}
