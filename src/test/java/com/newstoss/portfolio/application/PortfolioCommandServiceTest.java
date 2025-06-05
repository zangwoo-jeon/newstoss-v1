package com.newstoss.portfolio.application;

import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAPortfolioRepository;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.entity.Stock;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PortfolioCommandServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    private PortfolioCommandService portfolioCommandService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private JpaMemberRepository memberRepository;
    @Autowired
    private JPAPortfolioRepository portfolioRepository;

    @Test
    public void CreatePortfolio() {
        //given
        UUID id = UUID.randomUUID();
        Member member = Member.builder()
                .memberId(id)
                .account("testUser")
                .password("testPassword")
                .build();
        Stock stock = stockRepository.findByStockCode("005930").orElseThrow(
                () -> new RuntimeException("Stock not found")
        );
        //when
        Portfolio portfolio = Portfolio.createPortfolio(id, stock, 10, 50000);

        //then
        assertThat(portfolio).isNotNull();
    }

    @Test
    public void AddPortfolio() {
        //given
        UUID id = UUID.randomUUID();
        Stock stock = stockRepository.findByStockCode("005930").orElseThrow(
                () -> new RuntimeException("Stock not found")
        );
        Portfolio portfolio = Portfolio.createPortfolio(id, stock, 10, 50000);
        portfolioRepository.save(portfolio);

        //when
        portfolioCommandService.addPortfolio(id, "005930", 10, 60000);
        //then
        assertThat(portfolio.getStockCount()).isEqualTo(20);
        assertThat(portfolio.getEntryPrice()).isEqualTo(55000);
    }

    @Test
    public void RemovePortfolio() {
        //given
        UUID id = UUID.randomUUID();
        Member member = Member.builder()
                .memberId(id)
                .account("testUser")
                .password("testPassword")
                .build();
        Stock stock = stockRepository.findByStockCode("005930").orElseThrow(
                () -> new RuntimeException("Stock not found")
        );
        Portfolio portfolio = Portfolio.createPortfolio(id, stock, 20, 50000);

        //when
        int profit = portfolio.removeStock(10, 60000);

        //then
        assertThat(portfolio.getStockCount()).isEqualTo(10);
        assertThat(profit).isEqualTo(100000);
    }
}