package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;

import java.util.List;

public interface GetStockPeriodUseCase {
    /**
     * 주식 1개의 기간별 정보를 조회한다.
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @param period 조회할 기간(D: 일별, M: 월별, Y: 연별 )
     * @return 주식 정보 DTO 목록
     */
    List<KisPeriodStockDto> getStockInfoByPeriod(String stockCode, String period);

    /**
     * 주식 1개의 일별 데이터를 조회한다(지금으로부터 500일전까지)
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 주식의 일별 데이터 목록
     */
    List<KisPeriodStockDto> getDailyStockByPeriod (String stockCode);
    /**
     * 주식 1개의 주별 데이터를 조회한다.(지금으로부터 200주전까지)
     * @param stockCode 주식의 종목 코드
     * @return 주봉 데이터 목록
     */
    List<KisPeriodStockDto> getWeeklyStockByPeriod (String stockCode);
    /**
     * 주식 1개의 월별 데이터를 조회한다.(지금으로부터 100달전까지)
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 월별 주식 데이터 목록
     */
    List<KisPeriodStockDto> getMonthlyStockByPeriod (String stockCode);
    /**
     * 주식 1개의 연별 데이터를 조회한다.(지금으로부터 10년전까지)
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 연도별 주식 데이터 목록
     */
    List<KisPeriodStockDto> getYearlyStockByPeriod (String stockCode);
}
