package com.newstoss.portfolio.application.port.in;

import com.newstoss.portfolio.adapter.inbound.web.dto.redis.StockDto;

public interface GetStockInfo {
    /**
     * 주식 정보를 조회한다.
     * @param stockCode 주식 코드
     * @return 주식 정보
     */
    StockDto stockInfo(String stockCode);
}
