package com.newstoss.portfolio.application.scheduler;

import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.adapter.inbound.web.dto.redis.StockDto;
import com.newstoss.portfolio.adapter.outbound.persistence.MemberPnlQueryAdapter;
import com.newstoss.portfolio.adapter.outbound.stock.StockClient;
import com.newstoss.portfolio.application.port.out.CreateMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.PortfolioStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberPnlUpdater {

    private final CreateMemberPnlPort createMemberPnlPort;
    private final MemberPnlQueryAdapter memberPnlQueryAdapter;
    private final JpaMemberRepository memberRepository;
    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final StockClient stockClient;

    @Scheduled(cron = "0 0 0 * * *")
    public void createMemberPnl() {
        List<Member> allMembers = memberRepository.findAll();
        for (Member member : allMembers) {
            long asset = 0L;
            LocalDate yesterday = LocalDate.now().minusDays(1);
            List<PortfolioStock> portfolioStocks = getPortfolioStocksPort.getPortfolioStocks(member.getMemberId());
            for (PortfolioStock portfolioStock : portfolioStocks) {
                StockDto stockDto = stockClient.stockInfo(portfolioStock.getStockCode());
                asset += Long.parseLong(stockDto.getPrice()) * portfolioStock.getStockCount();
            }
            Long pnl = memberPnlQueryAdapter.todayMemerPnlAcc(member.getMemberId(), portfolioStocks);
            MemberPnl memberPnl = MemberPnl.createMemberPnl(member.getMemberId(), pnl, yesterday, asset);
            createMemberPnlPort.create(memberPnl);
        }

    }
}
