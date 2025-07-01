package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioDailyPnlResponseDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;
import com.newstoss.portfolio.application.port.in.GetPortfolioStockUseCase;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.application.port.out.LoadPortfolioPort;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PortfolioQueryService implements GetPortfolioStockUseCase {

    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final StockInfoPort stockInfoPort;
    private final LoadPortfolioPort loadPortfolioPort;
    @Override
    public PortfolioDailyPnlResponseDto getPortfolioStocks(UUID memberId) {
        List<PortfolioStock> portfolioStocks = getPortfolioStocksPort.getPortfolioStocks(memberId);
        if (portfolioStocks.isEmpty()) {
            log.info("포트폴리오가 비었습니다.");
            return null;
        }

        List<PortfolioStocksResponseDto> dtos = portfolioStocks.stream()
                .map(portfolioStock -> {
                    KisStockDto stockDto = stockInfoPort.getStockInfo(portfolioStock.getStockCode());
                    return getDto(portfolioStock, stockDto);
                }).toList();
        return new PortfolioDailyPnlResponseDto(dtos);
    }

    private static PortfolioStocksResponseDto getDto(PortfolioStock portfolioStock, KisStockDto stockDto) {
        int avgEntryPrice = portfolioStock.getEntryPrice();
        int totalCount = portfolioStock.getStockCount();
        int price = Integer.parseInt(stockDto.getPrice());
        long unrealizedPnl = (long) (price - avgEntryPrice) * totalCount;
        double returnRate = ((double)(price - avgEntryPrice)) / avgEntryPrice * 100.0;
        return new PortfolioStocksResponseDto(
                portfolioStock.getStockName(),
                portfolioStock.getStockImage(),
                portfolioStock.getStockCode(),
                portfolioStock.getStockCount(),
                portfolioStock.getEntryPrice(),
                price,
                unrealizedPnl,
                returnRate
        );
    }

}
