package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.entity.PortfolioStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GetPortfolioStocksPort {
    /**
     * 사용자의 포트폴리오에 포함된 주식 목록을 조회한다.
     * @param memberId 사용자 ID
     * @return 포트폴리오에 포함된 주식 목록
     */
    List<PortfolioStock> getPortfolioStocks(UUID memberId);

    /**
     * 사용자의 포트폴리에 포함된 주식중 주식코드에 맞는 개별 포트폴리오 주식을 가져온다.
     * @param memberId 사용자 ID
     * @param stockCode 주식 코드
     * @return 포트폴리오 주식
     */
    PortfolioStock getPortfolioStock(UUID memberId,String stockCode);
}
