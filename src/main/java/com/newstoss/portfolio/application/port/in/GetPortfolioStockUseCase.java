package com.newstoss.portfolio.application.port.in;

import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioDailyPnlResponseDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;

import java.util.List;
import java.util.UUID;

public interface GetPortfolioStockUseCase {
    /**
     * 포트폴리오에 포함된 주식 목록을 조회한다.
     * @param memberId 사용자 ID
     * @return 포트폴리오에 포함된 주식 목록
     */
    PortfolioDailyPnlResponseDto getPortfolioStocks(UUID memberId);
}
