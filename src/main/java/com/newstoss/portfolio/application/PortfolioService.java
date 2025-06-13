package com.newstoss.portfolio.application;

import com.newstoss.portfolio.application.port.in.CreatePortfolioUseCase;
import com.newstoss.portfolio.application.port.out.CreatePortfolioPort;
import com.newstoss.portfolio.entity.Portfolio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService implements CreatePortfolioUseCase {

    private final CreatePortfolioPort createPortfolioPort;

    @Override
    public void createPortfolio(UUID memberId) {
        Portfolio portfolio = Portfolio.createPortfolio(memberId, 0L, 0L);
        createPortfolioPort.create(portfolio);
    }
}
