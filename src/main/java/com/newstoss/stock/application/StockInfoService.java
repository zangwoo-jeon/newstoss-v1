package com.newstoss.stock.application;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.in.GetStockInfoUseCase;
import com.newstoss.stock.application.port.out.kis.KisStockInfoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockInfoService implements GetStockInfoUseCase {

    private final KisStockInfoPort stockInfoPort;

    @Override
    public String GetStockPrice(String stockCode) {
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);
        if (stockInfo == null) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockInfo.getPrice();
    }

    @Override
    public KisStockDto GetStockInfo(String stockCode) {
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);
        if (stockInfo == null) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockInfo;
    }


    @Override
    public List<KisPeriodStockDto> GetDailyStockByPeriod (String stockCode) {
        List<KisPeriodStockDto> stockDailyPeriod = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String StartDate = LocalDate.now().minusDays(i*100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String endDate = LocalDate.now().minusDays(i*100+100).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            stockDailyPeriod.addAll(stockInfoPort.getStockInfoByPeriod(stockCode, "D", StartDate, endDate));
        }
        if (stockDailyPeriod.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockDailyPeriod;
    }

    @Override
    public List<KisPeriodStockDto> GetMonthlyStockByPeriod(String stockCode) {
        List<KisPeriodStockDto> stockMonthlyPeriod = stockInfoPort.getStockInfoByPeriod(stockCode, "M",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                LocalDate.now().minusMonths(100).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        if (stockMonthlyPeriod.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockMonthlyPeriod;
    }

    @Override
    public List<KisPeriodStockDto> GetYearlyStockByPeriod(String stockCode) {
        List<KisPeriodStockDto> stockYearlyPeriod = stockInfoPort.getStockInfoByPeriod(stockCode, "Y",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                LocalDate.now().minusYears(10).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        if (stockYearlyPeriod.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return stockYearlyPeriod;
    }


}
