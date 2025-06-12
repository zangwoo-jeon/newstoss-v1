package com.newstoss.portfolio.application.port.out;


import java.util.UUID;

public interface DeleteMemberPortfolioPort {
    void deletePortfolio(UUID memberId);
}
