package com.newstoss.portfolio.application;

import com.newstoss.member.domain.MemberSignUpEvent;
import com.newstoss.portfolio.application.port.out.CreatePortfolioPort;
import com.newstoss.portfolio.entity.Portfolio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {

    private final CreatePortfolioPort createPortfolioPort;

    @EventListener
    public void createPortfolio(MemberSignUpEvent event) {
        log.info("✅ Portfolio 생성 이벤트 수신됨. memberId = {}", event.memberId());
        Portfolio portfolio = Portfolio.createPortfolio(event.memberId(), 0L, 0L);
        createPortfolioPort.create(portfolio);
    }
}
