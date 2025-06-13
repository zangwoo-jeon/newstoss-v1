package com.newstoss.portfolio.application.port.in;
import java.util.UUID;

public interface CreatePortfolioUseCase {
    void createPortfolio(UUID memberId);
}