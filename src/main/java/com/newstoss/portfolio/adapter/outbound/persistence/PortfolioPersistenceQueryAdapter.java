package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAPortfolioStockRepository;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.PortfolioStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PortfolioPersistenceQueryAdapter implements GetPortfolioStocksPort {

    private final JPAPortfolioStockRepository repository;

    @Override
    public List<PortfolioStock> getPortfolioStocks(UUID memberId) {
        return repository.findByMemberId(memberId);
    }

    public PortfolioStock getPortfolioStock(UUID memberId, String stockCode) {
        return repository.findByMemberIdAndStockCode(memberId, stockCode).orElseThrow(
                () -> new CustomException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND)
        );
    }
}
