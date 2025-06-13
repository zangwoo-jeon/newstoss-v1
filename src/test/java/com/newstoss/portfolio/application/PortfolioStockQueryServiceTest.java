package com.newstoss.portfolio.application;

import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioDailyPnlResponseDto;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JpaPortfolioRepository;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.entity.Stock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PortfolioStockQueryServiceTest {

    @Autowired
    private PortfolioQueryService service;

    @Autowired
    private PortfolioStockCommandService commandService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private JpaMemberRepository memberRepository;
    @Autowired
    private JpaPortfolioRepository portfolioRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void portfoliosTest() {
        //given
        UUID id = UUID.randomUUID();
        Stock stock1 = stockRepository.findByName("삼성전자").get();
        Stock stock2 = stockRepository.findByName("카카오").get();
        commandService.createPortfolioStock(id, stock1.getStockCode(), 10, 60000);
        commandService.createPortfolioStock(id, stock2.getStockCode(), 5, 100000);
        //when
        PortfolioDailyPnlResponseDto portfolioStocks = service.getPortfolioStocks(id);

        //then
        assertThat(portfolioStocks).isNotNull();
    }

    @Test
    @Rollback(false)
    public void CreateTestPortfolio() {
        //given
        Member member = memberRepository.findByName("test").get(0);
        //when
        portfolioService.createPortfolio(member.getMemberId());
        //then
        em.flush();
        em.clear();

        Portfolio portfolio = portfolioRepository.findByMemberId(member.getMemberId()).get();
        System.out.println("portfolio.getMemberId() = " + portfolio.getMemberId());

    }

}