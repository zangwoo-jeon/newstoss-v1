package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.entity.Stock;

import java.util.List;

public interface GetStockInfoUseCase {
    KisStockDto getStockInfo(String stockCode);
    List<KisPeriodStockDto> getStockInfoByPeriod(String stockCode, String period,
                                                String startDate, String endDate);
}
