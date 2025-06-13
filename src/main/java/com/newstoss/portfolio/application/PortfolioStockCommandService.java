package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;
import com.newstoss.portfolio.application.port.in.AddPortfolioUseCase;
import com.newstoss.portfolio.application.port.in.CreatePortfolioStockUseCase;
import com.newstoss.portfolio.application.port.in.SellPortfolioUseCase;
import com.newstoss.portfolio.application.port.out.*;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioStockCommandService implements CreatePortfolioStockUseCase, SellPortfolioUseCase , AddPortfolioUseCase {

    private final LoadStockPort loadStockPort;
    private final LoadPortfolioPort loadPortfolioPort;
    private final CreatePortfolioStockPort createPortfolioStockPort;
    private final StockInfoPort stockInfoPort;
    private final GetPortfolioStocksPort getPortfolioStocksPort;
    @Override
    public PortfolioStocksResponseDto createPortfolioStock(UUID memberId, String stockCode, Integer stockCount, Integer entryPrice) {
        Stock stock = loadStockPort.LoadStockByStockCode(stockCode);

        Portfolio portfolio = loadPortfolioPort.loadPortfolio(memberId);
        portfolio.updateAsset((long) stockCount * entryPrice);
        PortfolioStock portfolioStock = createPortfolioStockPort.savePortfolio(memberId,portfolio, stock, stockCount, entryPrice);
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);
        Integer price = Integer.valueOf(stockInfo.getPrice());
        portfolioStock.initUnRealizedPnl((long) (price - entryPrice) * stockCount);
        PortfolioStocksResponseDto dto = new PortfolioStocksResponseDto(portfolioStock);
        dto.updatePrices(price,portfolioStock.getUnrealizedPnl(),(double)(price - entryPrice)/ price);
        return dto;
    }

    @Override
    public PortfolioStocksResponseDto addPortfolio(UUID memberId, String stockCode, Integer stockCount , Integer entryPrice) {
        Portfolio portfolio = loadPortfolioPort.loadPortfolio(memberId);
        portfolio.updateAsset(((long) stockCount * entryPrice));
        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);
        Integer price = Integer.valueOf(stockInfo.getPrice());
        PortfolioStock portfolioStock = getPortfolioStocksPort.getPortfolioStock(memberId, stockCode).addStock(stockCount,entryPrice);

        portfolioStock.initUnRealizedPnl((long) (price - portfolioStock.getEntryPrice()) * portfolioStock.getStockCount());
        PortfolioStocksResponseDto dto = new PortfolioStocksResponseDto(portfolioStock);
        dto.updatePrices(price,portfolioStock.getUnrealizedPnl(),(double)(price - portfolioStock.getEntryPrice())/ price);
        return dto;
    }

    @Override
    public PortfolioStocksResponseDto sellStock(UUID memberId, String stockCode, int stockCount, int sellPrice) {
        Portfolio portfolio = loadPortfolioPort.loadPortfolio(memberId);
        portfolio.updateAsset((long) -stockCount * sellPrice);

        KisStockDto stockInfo = stockInfoPort.getStockInfo(stockCode);
        Integer price = Integer.valueOf(stockInfo.getPrice());

        PortfolioStock portfolioStock = getPortfolioStocksPort.getPortfolioStock(memberId, stockCode);
        Long realizedPnl = portfolioStock.removeStock(stockCount, price);
        PortfolioStocksResponseDto dto;
        if (portfolioStock.getStockCount() == 0) {
            portfolio.removePortfolioStock(portfolioStock);
            dto = null;
        } else {
            portfolioStock.initUnRealizedPnl((long) (price - portfolioStock.getEntryPrice()) * stockCount);
            dto = new PortfolioStocksResponseDto(portfolioStock);
            dto.updatePrices(price,portfolioStock.getUnrealizedPnl(),(double)(price - portfolioStock.getEntryPrice())/ price);
        }
        portfolio.updatePnl(realizedPnl);

        return dto;
    }
}
