package com.newstoss.portfolio.application.port.in;

import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;

import java.util.UUID;

public interface SellPortfolioUseCase {
    /**
     * 주식 판매
     * @param memberId 회원 ID
     * @param stockCode 주식 코드
     * @param stockCount 판매할 주식 수량
     * @return 판매 한 주식 코드
     */
    PortfolioStocksResponseDto sellStock(UUID memberId, String stockCode, int stockCount, int sellPrice);
}
