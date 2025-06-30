package com.newstoss.portfolio.application;

import com.newstoss.portfolio.adapter.inbound.web.dto.redis.StockDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;
import com.newstoss.portfolio.application.port.in.AddPortfolioUseCase;
import com.newstoss.portfolio.application.port.in.CreatePortfolioStockUseCase;
import com.newstoss.portfolio.application.port.in.GetStockInfo;
import com.newstoss.portfolio.application.port.in.SellPortfolioUseCase;
import com.newstoss.portfolio.application.port.out.*;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioStockCommandService implements CreatePortfolioStockUseCase, SellPortfolioUseCase , AddPortfolioUseCase {

    private final LoadPortfolioPort loadPortfolioPort;
    private final CreatePortfolioStockPort createPortfolioStockPort;
    private final StockInfoPort stockInfoPort;
    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final ApplicationEventPublisher publisher;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GetStockInfo getStockInfo;

    @Override
    public PortfolioStocksResponseDto createPortfolioStock(UUID memberId, String stockCode, Integer stockCount, Integer entryPrice) {
        Portfolio portfolio = loadPortfolioPort.loadPortfolio(memberId);
        StockDto stockDto = getStockInfo.stockInfo(stockCode);
        createPortfolioStockPort.savePortfolio(memberId,portfolio,stockDto,stockCount,entryPrice,stockDto.getStockImage());
        int price = Integer.parseInt(stockDto.getPrice());
        return new PortfolioStocksResponseDto(
                stockDto.getStockName(),stockDto.getStockImage(),stockDto.getStockCode(),stockCount,entryPrice,price,(long)(price - entryPrice) * stockCount, ((double)(price - entryPrice)) / entryPrice *100.0
        );
    }

    @Override
    public PortfolioStocksResponseDto addPortfolio(UUID memberId, String stockCode, Integer stockCount , Integer entryPrice) {
        Portfolio portfolio = loadPortfolioPort.loadPortfolio(memberId);
        StockDto stockDto = getStockInfo.stockInfo(stockCode);
        int price = Integer.parseInt(stockDto.getPrice());
        PortfolioStock portfolioStock = getPortfolioStocksPort.getPortfolioStock(memberId, stockCode).addStock(stockCount,entryPrice);
        int avgEntryPrice = portfolioStock.getEntryPrice();
        int totalCount = portfolioStock.getStockCount();

        long unrealizedPnl = (long) (price - avgEntryPrice) * totalCount;
        double returnRate = ((double)(price - avgEntryPrice)) / avgEntryPrice * 100.0;

        return new PortfolioStocksResponseDto(
                stockDto.getStockName(), stockDto.getStockImage(), stockDto.getStockCode(), stockCount, entryPrice, price,
                unrealizedPnl, returnRate);
    }

    @Override
    public PortfolioStocksResponseDto sellStock(UUID memberId, String stockCode, int stockCount, int sellPrice) {
        Portfolio portfolio = loadPortfolioPort.loadPortfolio(memberId);

        StockDto stockInfo = getStockInfo.stockInfo(stockCode);
        int price = Integer.parseInt(stockInfo.getPrice());

        PortfolioStock portfolioStock = getPortfolioStocksPort.getPortfolioStock(memberId, stockCode);
        Long realizedPnl = portfolioStock.removeStock(stockCount, price);
        portfolio.updateRealizedPnl(realizedPnl);
        PortfolioStocksResponseDto dto;
        if (portfolioStock.getStockCount() == 0) {
            portfolio.removePortfolioStock(portfolioStock);
            dto = null;
        } else {
            Integer totalCount = portfolioStock.getStockCount();
            int entryPrice = portfolioStock.getEntryPrice();
            double returnRate = ((double)(price - entryPrice)) / entryPrice * 100.0;
            dto = new PortfolioStocksResponseDto(
                    stockInfo.getStockName(), stockInfo.getStockImage(), stockInfo.getStockCode(), stockCount, entryPrice, price,
                    (long) (price - entryPrice) * totalCount,returnRate
            );
        }

        return dto;
    }
}
