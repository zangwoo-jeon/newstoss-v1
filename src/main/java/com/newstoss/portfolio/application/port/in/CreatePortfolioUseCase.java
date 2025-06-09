package com.newstoss.portfolio.application.port.in;

import java.util.UUID;

public interface CreatePortfolioUseCase {
    /**
     * 포트폴리오를 생성한다.
     * @param memberId 사용자 ID
     * @param stockCode 주식 코드
     * @param stockCount 주식 수량
     * @param entryPrice 진입 가격
     * @return 생성된 포트폴리오 ID
     */
    Long createPortfolio(UUID memberId, String stockCode, Integer stockCount, Integer entryPrice);

}
