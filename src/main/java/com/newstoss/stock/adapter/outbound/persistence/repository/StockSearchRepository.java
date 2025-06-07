package com.newstoss.stock.adapter.outbound.persistence.repository;

import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface StockSearchRepository {
    /**
     * 검색어를 이용해 주식 정보를 조회하는 메서드
     */
    List<Stock> searchStock(String query);
}
