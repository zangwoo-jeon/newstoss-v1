package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface GetStockInfoUseCase {
    /**
     * 주식 1개의 현재가를 반환한다.
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 주식의 현재가
     */
    String getStockPrice(String stockCode);

}
