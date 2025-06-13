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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioQueryService implements GetPortfolioStockUseCase {

    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final StockInfoPort stockInfoPort;
    private final LoadPortfolioPort loadPortfolioPort;
    @Override
    public PortfolioDailyPnlResponseDto getPortfolioStocks(UUID memberId) {
        List<PortfolioStock> portfolioStocks = getPortfolioStocksPort.getPortfolioStocks(memberId);
        if (portfolioStocks.isEmpty()) {
            throw new CustomException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND);
        }

        List<PortfolioStocksResponseDto> dtos = portfolioStocks.stream()
                .map(portfolioStock -> {
                    PortfolioStocksResponseDto dto = new PortfolioStocksResponseDto(portfolioStock);
                    KisStockDto stockInfo = stockInfoPort.getStockInfo(portfolioStock.getStock().getStockCode());
                    int price = Integer.parseInt(stockInfo.getPrice());
                    int count = portfolioStock.getStockCount();
                    dto.updatePrices(price,((long) (price - portfolioStock.getEntryPrice()) * count),((double) (price - portfolioStock.getEntryPrice()) / portfolioStock.getEntryPrice()) * 100);
                    return dto;
                }).toList();
        return new PortfolioDailyPnlResponseDto(dtos);
    }

}
