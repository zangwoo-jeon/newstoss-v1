package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.adapter.inbound.web.dto.redis.StockDto;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.entity.Stock;

import java.util.UUID;

public interface CreatePortfolioStockPort {
    /**
     * 포트폴리오를 저장한다.
     * @param memberId 사용자 ID
     * @param stock 주식
     * @param stockCount 주식 수량
     * @param entryPrice 진입 가격
     * @return 생성된 포트폴리오 ID
     */
    PortfolioStock savePortfolio(UUID memberId, Portfolio portfolio, StockDto dto, Integer stockCount, Integer entryPrice, String stockImage);
}
