package com.newstoss.portfolio.application;

import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioDailyPnlResponseDto;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.entity.Stock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PortfolioQueryServiceTest {

    @Autowired
    private PortfolioQueryService service;

    @Autowired
    private PortfolioCommandService commandService;

    @Autowired
    private StockRepository stockRepository;


    @Test
    public void portfoliosTest() {
        //given
        UUID id = UUID.randomUUID();
        Stock stock1 = stockRepository.findByName("삼성전자").get();
        Stock stock2 = stockRepository.findByName("카카오").get();
        commandService.createPortfolio(id, stock1.getStockCode(), 10, 60000);
        commandService.createPortfolio(id, stock2.getStockCode(), 5, 100000);
        //when
        PortfolioDailyPnlResponseDto portfolioStocks = service.getPortfolioStocks(id);

        //then
        assertThat(portfolioStocks).isNotNull();
        System.out.println("portfolioStocks.getTotalPnl() = " + portfolioStocks.getTotalPnl());
    }
}