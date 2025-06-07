package com.newstoss.stock.application.port.out.persistence;

import com.newstoss.stock.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoadCategoryPort {
    /**
     * 모든 카테고리를 가져온다.
     * @return 카테고리 리스트
     */
    List<String> getCategories();
    Page<Stock> getStockByCategory(String category, Pageable pageable);
}
