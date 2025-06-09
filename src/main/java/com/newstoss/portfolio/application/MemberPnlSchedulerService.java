package com.newstoss.portfolio.application;

import com.newstoss.member.application.out.MemberQueryPort;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.application.port.out.CreateMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.Portfolio;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.KisStockInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberPnlSchedulerService {

    private final MemberQueryPort memberQueryPort;
    private final GetMemberPnlPort getMemberPnlPort;
    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final KisStockInfoPort kisStockInfoPort;
    private final CreateMemberPnlPort createMemberPnlPort;
    @Scheduled(cron = "0 1 0 * * *")
    public void createDailyPnl() {
        log.info("Creating Daily Pnl");

        List<Member> allMembers = memberQueryPort.findAll();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        for (Member member : allMembers) {
            List<Portfolio> portfolioStocks = getPortfolioStocksPort.getPortfolioStocks(member.getMemberId());
            int pnl = 0;
            long stocksValue = 0L;

            for (Portfolio portfolioStock : portfolioStocks) {
                KisStockDto stockInfo = kisStockInfoPort.getStockInfo(portfolioStock.getStock().getStockCode());
                pnl += Integer.parseInt(stockInfo.getChangeAmount());
                stocksValue += Long.parseLong(stockInfo.getPrice());
            }

            Optional<MemberPnl> optionalPnl = getMemberPnlPort.getMemberPnl(member.getMemberId(), yesterday);
            if (optionalPnl.isPresent()) {
                MemberPnl yesterdayPnl = optionalPnl.get();
                yesterdayPnl.updatePnl(pnl, stocksValue);
                MemberPnl todayPnl = MemberPnl.createMemberPnl(member.getMemberId(), 0, today, yesterdayPnl.getAsset());
                createMemberPnlPort.create(todayPnl);
            } else {
                MemberPnl todayPnl = MemberPnl.createMemberPnl(member.getMemberId(), 0, today, 0L);
                createMemberPnlPort.create(todayPnl);
            }
        }
    }
}
