package com.newstoss.stock.application.V1;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPopularDto;
import com.newstoss.stock.application.port.in.v1.GetPopularStockUseCase;
import com.newstoss.stock.application.port.out.kis.PopularStockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockPopularService implements GetPopularStockUseCase {

    private final PopularStockPort popularStockPort;

    @Override
    public List<KisPopularDto> getPopularStock() {
        List<KisPopularDto> popularStocks = popularStockPort.getPopularStock();
        if (popularStocks == null || popularStocks.isEmpty()) {
            throw new CustomException(StockErrorCode.STOCK_NOT_FOUND);
        }
        return popularStocks.stream()
                .sorted(Comparator.comparing(Stock -> Integer.parseInt(Stock.getRank())))
                .limit(6)
                .toList();
    }
}
