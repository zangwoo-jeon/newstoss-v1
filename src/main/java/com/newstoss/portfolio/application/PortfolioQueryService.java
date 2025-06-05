package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioDailyPnlResponseDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;
import com.newstoss.portfolio.application.port.in.GetPortfolioStockUseCase;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.KisStockInfoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioQueryService implements GetPortfolioStockUseCase {

    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final KisStockInfoPort kisStockInfoPort;
    @Override
    public PortfolioDailyPnlResponseDto getPortfolioStocks(UUID memberId) {
        List<Portfolio> portfolioStocks = getPortfolioStocksPort.getPortfolioStocks(memberId);
        if (portfolioStocks.isEmpty()) {
            throw new CustomException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND);
        }
        AtomicInteger totalPnl = new AtomicInteger(0);
        List<PortfolioStocksResponseDto> dtos = portfolioStocks.stream()
                .map(portfolio -> {
                    PortfolioStocksResponseDto dto = new PortfolioStocksResponseDto(portfolio);
                    KisStockDto stockInfo = kisStockInfoPort.getStockInfo(portfolio.getStock().getStockCode());
                    int price = Integer.parseInt(stockInfo.getPrice());
                    int count = portfolio.getStockCount();
                    dto.setCurrentPrice(price);
                    dto.setProfitLoss((price - portfolio.getEntryPrice()) * count);
                    totalPnl.addAndGet(dto.getProfitLoss());
                    dto.setProfitLossRate(((double) (price - portfolio.getEntryPrice()) / portfolio.getEntryPrice()) * 100);
                    return dto;
                }).toList();
        PortfolioDailyPnlResponseDto dto = new PortfolioDailyPnlResponseDto(
                totalPnl.get(),
                dtos
        );
        return dto;
    }

}
