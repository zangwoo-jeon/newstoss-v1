package com.newstoss.stock.application.port.out.persistence;

import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface SearchStockPort {
    /**
     * 주식 종목을 검색합니다.
     * @param keyword 검색 키워드 : 주식 이름 or 주식 코드
     * @return 종목 리스트
     */
    List<Stock> searchStock(String keyword);

    /**
     * 인기 종목을 검색합니다.(키워드 없는 검색)
     * @return 종목 리스트
     */
    List<Stock> searchPopuarStock();
}
