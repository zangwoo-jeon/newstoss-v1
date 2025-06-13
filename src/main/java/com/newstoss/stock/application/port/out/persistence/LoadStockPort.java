package com.newstoss.stock.application.port.out.persistence;

import com.newstoss.stock.entity.Stock;

public interface LoadStockPort {
    Stock LoadStockByStockCode(String stockCode);
}
