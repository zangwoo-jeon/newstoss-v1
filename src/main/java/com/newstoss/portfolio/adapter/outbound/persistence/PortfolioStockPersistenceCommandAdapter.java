package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.portfolio.adapter.inbound.web.dto.redis.StockDto;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAPortfolioStockRepository;
import com.newstoss.portfolio.application.port.out.CreatePortfolioStockPort;
import com.newstoss.portfolio.application.port.out.DeleteMemberPortfolioPort;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PortfolioStockPersistenceCommandAdapter implements CreatePortfolioStockPort, DeleteMemberPortfolioPort {
    private final JPAPortfolioStockRepository portfolioRepository;

    @Override
    public PortfolioStock savePortfolio(UUID memberId, Portfolio portfolio, StockDto dto, Integer stockCount, Integer entryPrice, String stockImage) {
        return portfolioRepository.save(PortfolioStock.createPortfolio(memberId,portfolio, dto, stockCount, entryPrice, stockImage));
    }

    @Override
    public void deletePortfolio(UUID memberId) {
        List<PortfolioStock> portfolioStocks = portfolioRepository.findByMemberId(memberId);
        portfolioRepository.deleteAll(portfolioStocks);
    }
}
