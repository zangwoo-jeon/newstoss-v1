package com.newstoss.portfolio.application;

import com.newstoss.member.application.out.MemberQueryPort;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.application.port.out.CreateMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberPnlSchedulerService {

    private final MemberQueryPort memberQueryPort;
    private final GetMemberPnlPort getMemberPnlPort;
    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final StockInfoPort stockInfoPort;
    private final CreateMemberPnlPort createMemberPnlPort;
    @Scheduled(cron = "0 1 0 * * *")
    public void createDailyPnl() {
        log.info("Creating Daily Pnl");

        List<Member> allMembers = memberQueryPort.findAll();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate beforeDay = today.minusDays(2);
        for (Member member : allMembers) {
            List<PortfolioStock> portfolioStockStocks = getPortfolioStocksPort.getPortfolioStocks(member.getMemberId());
            long pnl = 0;
            long stocksValue = 0L;

            for (PortfolioStock portfolioStock : portfolioStockStocks) {
                KisStockDto stockInfo = stockInfoPort.getStockInfo(portfolioStock.getStock().getStockCode());
                stocksValue += Long.parseLong(stockInfo.getPrice()) * portfolioStock.getStockCount();
            }
            final long asset = stocksValue;
            getMemberPnlPort.getMemberPnl(member.getMemberId(),yesterday).ifPresentOrElse(
                    yPnl -> {
                        yPnl.initAsset(asset);
                        Long prevAsset = getMemberPnlPort.getMemberPnl(member.getMemberId(), beforeDay)
                                .map(MemberPnl::getAsset)
                                .orElse(0L);
                        createMemberPnlPort.create(MemberPnl.createMemberPnl(member.getMemberId(),asset - prevAsset,today,asset));
                    },
                    () -> {
                        createMemberPnlPort.create(MemberPnl.createMemberPnl(member.getMemberId(),0L,today,0L));
                    }
            );
        }
    }
}
