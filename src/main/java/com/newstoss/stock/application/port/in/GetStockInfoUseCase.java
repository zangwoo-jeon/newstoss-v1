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
    String GetStockPrice(String stockCode);
    /**
     * 주식 1개의 정보를 조회한다.
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 주식 정보 DTO
     */
    KisStockDto GetStockInfo(String stockCode);
    /**
     * 주식 1개의 일별 데이터를 조회한다(지금으로부터 500일전까지)
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 주식의 일별 데이터 목록
     */
    List<KisPeriodStockDto> GetDailyStockByPeriod (String stockCode);
    /**
     * 주식 1개의 월별 데이터를 조회한다.(지금으로부터 100달전까지)
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 월별 주식 데이터 목록
     */
    List<KisPeriodStockDto> GetMonthlyStockByPeriod (String stockCode);
    /**
     * 주식 1개의 연별 데이터를 조회한다.(지금으로부터 10년전까지)
     * @author Hyeongjun Park
     * @param stockCode 조회할 주식 코드
     * @return 연도별 주식 데이터 목록
     */
    List<KisPeriodStockDto> GetYearlyStockByPeriod (String stockCode);
}
