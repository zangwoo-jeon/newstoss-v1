package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface GetCategoryUseCase {
    /**
     * 카테고리 정보를 조회하는 메서드
     *
     * @return 카테고리 정보 리스트
     */
    List<String> getCategories();
    List<Stock> getStockByCategory(String category);
}
