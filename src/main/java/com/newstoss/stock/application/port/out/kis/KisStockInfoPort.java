package com.newstoss.stock.application.port.out.kis;

import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;

import java.util.List;

public interface KisStockInfoPort {
    /**
     * 주식 1개에 대한 정보를 가져온다.
     * @param stockCode 주식 코드
     * @return 주식 정보 DTO
     */
    KisStockDto getStockInfo(String stockCode);

    /**
     * 주석 기간별 주식 정보를 가져온다.
     * @param stockCode 주식 코드
     * @param period 기간 (일별, 월별, 연별 등)
     * @param startDate 시작 날짜 (yyyyMMDD 형식)
     * @param endDate 종료 날짜 (yyyyMMDD 형식)
     * @return 기간별 주식 목록 (최대 100개)
     */
    List<KisPeriodStockDto> getStockInfoByPeriod(String stockCode, String period, String startDate, String endDate);
}
