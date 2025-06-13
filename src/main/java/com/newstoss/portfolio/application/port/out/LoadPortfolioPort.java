package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.entity.Portfolio;

import java.util.UUID;

public interface LoadPortfolioPort {
    Portfolio loadPortfolio(UUID memberId);
}
