package com.newstoss.portfolio.application.port.out;

import java.util.UUID;

public interface UpdatePortfolioPort {
    String addPortfolio(UUID memberId, String stockCode, Integer stockCount , Integer entryPrice);
    String sellPortfolio(UUID memberId, String stockCode, int stockCount, int sellPrice);
}
