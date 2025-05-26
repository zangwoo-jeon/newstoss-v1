package com.newstoss.stock.application.port.in;

import com.newstoss.stock.entity.Stock;

public interface CreateStockUseCase {
    public Long save(Stock stock);
}