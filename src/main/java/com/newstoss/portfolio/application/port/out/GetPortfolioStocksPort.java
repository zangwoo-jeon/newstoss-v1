package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.entity.Portfolio;

import java.util.List;
import java.util.UUID;

public interface GetPortfolioStocksPort {
    /**
     * 포트폴리오에 포함된 주식 목록을 조회한다.
     * @param memberId 사용자 ID
     * @return 포트폴리오에 포함된 주식 목록
     */
    List<Portfolio> getPortfolioStocks(UUID memberId);
}
