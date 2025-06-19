package com.newstoss.stock.application.port.in.v1;

public interface CreateStockUseCase {
    public Long save(String stockCode);
}