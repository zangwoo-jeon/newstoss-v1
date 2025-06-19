package com.newstoss.stock.application.V1;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.in.v1.GetStockInfoUseCase;
import com.newstoss.stock.application.port.in.v1.GetStockPeriodUseCase;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class StockInfoService implements GetStockInfoUseCase , GetStockPeriodUseCase {

    private final StockInfoPort stockInfoPort;

    @Override
    public String getStockPrice(String stockCode) {
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);
        if (stockInfo == null) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockInfo.getPrice();
    }

    @Override
    public List<KisPeriodStockDto> getStockInfoByPeriod(String stockCode, String period) {
        if (period.equals("D")) {
            return getDailyStockByPeriod(stockCode);
        } else if (period.equals("W")) {
            return getWeeklyStockByPeriod(stockCode);
        } else if (period.equals("M")) {
            return getMonthlyStockByPeriod(stockCode);
        } else if (period.equals("Y")) {
             return getYearlyStockByPeriod(stockCode);
        } else {
            throw new CustomException(StockErrorCode.INVALID_PERIOD_TYPE);
        }
    }

    @Override
    public List<KisPeriodStockDto> getDailyStockByPeriod (String stockCode) {
        List<KisPeriodStockDto> stockDailyPeriod = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String endDate = LocalDate.now().minusDays(i*100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String startDate = LocalDate.now().minusDays(i*100+100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            stockDailyPeriod.addAll(stockInfoPort.getStockInfoByPeriod(stockCode, "D", startDate, endDate));
        }
        if (stockDailyPeriod.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockDailyPeriod;
    }

    @Override
    public List<KisPeriodStockDto> getWeeklyStockByPeriod(String stockCode) {
        List<KisPeriodStockDto> stockWeeklyPeriod = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String endDate = LocalDate.now().minusWeeks(i*100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String startDate = LocalDate.now().minusWeeks(i*100+100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            stockWeeklyPeriod.addAll(stockInfoPort.getStockInfoByPeriod(stockCode, "W", startDate, endDate));
        }
        if (stockWeeklyPeriod.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockWeeklyPeriod;
    }

    @Override
    public List<KisPeriodStockDto> getMonthlyStockByPeriod(String stockCode) {
        List<KisPeriodStockDto> stockMonthlyPeriod = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String endDate = LocalDate.now().minusMonths(i*100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String startDate = LocalDate.now().minusMonths(i*100+100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            stockMonthlyPeriod.addAll(stockInfoPort.getStockInfoByPeriod(stockCode, "M", startDate, endDate));
        }
        if (stockMonthlyPeriod.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockMonthlyPeriod;
    }

    @Override
    public List<KisPeriodStockDto> getYearlyStockByPeriod(String stockCode) {
        List<KisPeriodStockDto> stockYearlyPeriod = stockInfoPort.getStockInfoByPeriod(stockCode, "Y",
                LocalDate.now().minusYears(100).format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        if (stockYearlyPeriod.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockYearlyPeriod;
    }


}
