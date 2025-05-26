package com.newstoss.stock.application.port.in;

public interface UpdateStockUseCase {
    public Long updatePrice(Long stockId, Integer price);
}
