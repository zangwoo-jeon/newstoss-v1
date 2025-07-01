package com.newstoss.portfolio.application;

import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.application.MemberService;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.PortfolioStocksResponseDto;
import com.newstoss.portfolio.adapter.outbound.persistence.MemberPnlCommandAdapter;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAMemberPnlRepository;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAPortfolioStockRepository;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JpaPortfolioRepository;
import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.entity.Stock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PortfolioStockCommandServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    private PortfolioStockCommandService portfolioCommandService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private JpaMemberRepository memberRepository;
    @Autowired
    private JpaPortfolioRepository portfolioRepo;
    @Autowired
    private JPAPortfolioStockRepository portfolioRepository;
    @Autowired
    private MemberPnlCommandAdapter memberPnlCommandAdapter;
    @Autowired
    private JPAMemberPnlRepository memberPnlRepository;


    @BeforeEach
    public void init() {
        UUID uuid = UUID.randomUUID();
        Member member = Member.builder()
                .memberId(uuid)
                .account("testUser")
                .password("testPassword")
                .name("testName")
                .build();
        MemberPnl memberPnl = MemberPnl.createMemberPnl(uuid, 0L, LocalDate.now(), 0L);
        memberRepository.save(member);
        portfolioRepo.save(Portfolio.createPortfolio(uuid, 0L,0L, 0L));
        memberPnlCommandAdapter.create(memberPnl);
    }

    @Test
    public void CreatePortfolio() {
        //given
        Member member = memberRepository.findByName("testName").get(0);
        Stock stock = stockRepository.findByStockCode("005930").orElseThrow(
                () -> new RuntimeException("Stock not found")
        );
        //when
        PortfolioStocksResponseDto portfolioStock = portfolioCommandService.createPortfolioStock(member.getMemberId(), "005930", 10, 50000);
        Portfolio portfolio = portfolioRepo.findByMemberId(member.getMemberId()).orElseThrow(
                () -> new RuntimeException("Portfolio not found")
        );
        //then
        assertThat(portfolioStock).isNotNull();
        System.out.println("portfolioStock = " + portfolioStock);
        System.out.println("portfolio.getAsset() = " + portfolio.getAsset());

    }
    
    @Test
    public void MemberPnlCreateTest() {
        //given
        List<Member> members = memberRepository.findByName("testName");
        Stock stock = stockRepository.findByStockCode("005930").orElseThrow(
                () -> new RuntimeException("Stock not found")
        );
        Member member = members.get(0);
        portfolioCommandService.createPortfolioStock(member.getMemberId(), "005930", 10, 50000);

        //when
        List<MemberPnl> memberPnls = memberPnlRepository.findByMemberId(member.getMemberId());
        for (MemberPnl memberPnl : memberPnls) {
            System.out.println("memberPnl.getDate() = " + memberPnl.getDate());
            System.out.println("memberPnl.getAsset() = " + memberPnl.getAsset());
        }

    }
    
    @Test
    public void AddPortfolio() {
        //given
        UUID id = memberRepository.findByName("testName").get(0).getMemberId();
        Stock stock = stockRepository.findByStockCode("005930").orElseThrow(
                () -> new RuntimeException("Stock not found")
        );
        Portfolio portfolio = portfolioRepo.findByMemberId(id).get();
        PortfolioStock portfolioStock = PortfolioStock.createPortfolio(id,portfolio, stock, 10,10, 50000);
        portfolioRepository.save(portfolioStock);
        //when
        portfolioCommandService.addPortfolio(id, "005930", 10, 60000);
        //then
        assertThat(portfolioStock.getStockCount()).isEqualTo(20);
        assertThat(portfolioStock.getEntryPrice()).isEqualTo(55000);
    }

}