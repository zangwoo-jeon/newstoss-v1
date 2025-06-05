package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.errorcode.UserErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.application.port.in.AddPortfolioUseCase;
import com.newstoss.portfolio.application.port.in.CreatePortfolioUseCase;
import com.newstoss.portfolio.application.port.in.SellPortfolioUseCase;
import com.newstoss.portfolio.application.port.out.CreatePortfolioPort;
import com.newstoss.portfolio.application.port.out.UpdatePortfolioPort;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.GetStockInfoUseCase;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioCommandService implements CreatePortfolioUseCase , SellPortfolioUseCase , AddPortfolioUseCase {

    private final StockRepository stockRepository;

    private final CreatePortfolioPort createPortfolioPort;
    private final UpdatePortfolioPort updatePortfolioPort;
    @Override
    public Long createPortfolio(UUID memberId, String stockCode, Integer stockCount, Integer entryPrice) {
        Stock stock = stockRepository.findByStockCode(stockCode).orElseThrow(
                () -> new CustomException(StockErrorCode.STOCK_NOT_FOUND)
        );
        return createPortfolioPort.savePortfolio(memberId, stock, stockCount, entryPrice);
    }

    @Override
    public String addPortfolio(UUID memberId, String stockCode, Integer stockCount , Integer entryPrice) {
        return updatePortfolioPort.addPortfolio(memberId, stockCode, stockCount, entryPrice);
    }

    @Override
    public String sellStock(UUID memberId, String stockCode, int stockCount, int sellPrice) {
        return updatePortfolioPort.sellPortfolio(memberId, stockCode, stockCount, sellPrice);
    }
}
