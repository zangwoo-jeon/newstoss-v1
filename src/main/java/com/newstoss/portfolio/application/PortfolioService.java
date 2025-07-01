package com.newstoss.portfolio.application;

import com.newstoss.member.domain.event.MemberSignUpEvent;
import com.newstoss.member.domain.event.WithdrawEvent;
import com.newstoss.portfolio.application.port.out.CreatePortfolioPort;
import com.newstoss.portfolio.application.port.out.DeleteMemberPnlPort;
import com.newstoss.portfolio.application.port.out.DeleteMemberPortfolioPort;
import com.newstoss.portfolio.entity.Portfolio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {

    private final CreatePortfolioPort createPortfolioPort;
    private final DeleteMemberPortfolioPort deleteMemberPortfolioPort;
    private final DeleteMemberPnlPort deleteMemberPnlPort;

    @EventListener
    public void createPortfolio(MemberSignUpEvent event) {
        log.info("✅ Portfolio 생성 이벤트 수신됨. memberId = {}", event.memberId());
        Portfolio portfolio = Portfolio.createPortfolio(event.memberId(), 0L, 0L, 0L);
        createPortfolioPort.create(portfolio);
    }

    @EventListener
    public void deletePortfolio(WithdrawEvent event) {
        log.info("✅ Portfolio 삭제 이벤트 수신됨. memberId = {}", event.memberId());
        deleteMemberPnlPort.deleteMemberPnl(event.memberId());
        deleteMemberPortfolioPort.deletePortfolio(event.memberId());
    }
}
