package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAPortfolioRepository;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PortfolioPersistenceQueryAdapter implements GetPortfolioStocksPort {

    private final JPAPortfolioRepository repository;

    @Override
    public List<Portfolio> getPortfolioStocks(UUID memberId) {
        return repository.findByMemberId(memberId);
    }
}
