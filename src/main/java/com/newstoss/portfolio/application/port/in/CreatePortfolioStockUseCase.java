package com.newstoss.portfolio.application.port.in;

import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;

import java.util.UUID;

public interface CreatePortfolioStockUseCase {
    /**
     * 포트폴리오를 생성한다.
     * @param memberId 사용자 ID
     * @param stockCode 주식 코드
     * @param stockCount 주식 수량
     * @param entryPrice 진입 가격
     * @return 생성된 포트폴리오 ID
     */
    PortfolioStocksResponseDto createPortfolioStock(UUID memberId, String stockCode, Integer stockCount, Integer entryPrice);

}
