package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAPortfolioRepository;
import com.newstoss.portfolio.application.port.out.CreatePortfolioPort;
import com.newstoss.portfolio.application.port.out.DeleteMemberPortfolioPort;
import com.newstoss.portfolio.application.port.out.UpdatePortfolioPort;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PortfolioPersistenceCommandAdapter implements CreatePortfolioPort , UpdatePortfolioPort , DeleteMemberPortfolioPort {
    private final JPAPortfolioRepository portfolioRepository;
    @Override
    public Long savePortfolio(UUID memberId, Stock stock, Integer stockCount, Integer entryPrice) {
        Portfolio saved = portfolioRepository.save(Portfolio.createPortfolio(memberId, stock, stockCount, entryPrice));
        return saved.getId();
    }

    @Override
    public String addPortfolio(UUID memberId, String stockCode, Integer stockCount, Integer entryPrice) {
        Portfolio portfolio = portfolioRepository.findByMemberIdAndStockCode(memberId, stockCode)
                .orElseThrow(() -> new CustomException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));
        portfolio.addStock(stockCount, entryPrice);
        return stockCode;
    }

    @Override
    public String sellPortfolio(UUID memberId, String stockCode, int stockCount, int sellPrice) {
        Portfolio portfolio = portfolioRepository.findByMemberIdAndStockCode(memberId, stockCode)
                .orElseThrow(() -> new CustomException(StockErrorCode.STOCK_NOT_FOUND));
        portfolio.removeStock(stockCount, sellPrice);
        return stockCode;
    }

    @Override
    public void deletePortfolio(UUID memberId) {
        List<Portfolio> portfolios = portfolioRepository.findByMemberId(memberId);
        portfolioRepository.deleteAll(portfolios);
    }
}
