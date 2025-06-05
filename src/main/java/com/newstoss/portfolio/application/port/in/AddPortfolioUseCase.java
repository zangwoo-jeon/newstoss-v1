package com.newstoss.portfolio.application.port.in;

import java.util.UUID;

public interface AddPortfolioUseCase {
    /**
     * 포트폴리오에 주식을 추가한다.
     * @param memberId 사용자 ID
     * @param stockCode 주식 코드
     * @param stockCount 추가할 주식 수량
     * @param entryPrice 추가할 주식의 진입 가격
     * @return 추가된 포트폴리오 ID
     */
    String addPortfolio(UUID memberId, String stockCode, Integer stockCount , Integer entryPrice);
}
