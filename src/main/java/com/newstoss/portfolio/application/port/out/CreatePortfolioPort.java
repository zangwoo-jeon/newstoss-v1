package com.newstoss.portfolio.application.port.out;

import com.newstoss.stock.entity.Stock;

import java.util.UUID;

public interface CreatePortfolioPort {
    /**
     * 포트폴리오를 저장한다.
     * @param memberId 사용자 ID
     * @param stock 주식
     * @param stockCount 주식 수량
     * @param entryPrice 진입 가격
     * @return 생성된 포트폴리오 ID
     */
    Long savePortfolio(UUID memberId, Stock stock, Integer stockCount, Integer entryPrice);
}
