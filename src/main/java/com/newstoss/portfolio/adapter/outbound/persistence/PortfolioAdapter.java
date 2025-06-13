package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JpaPortfolioRepository;
import com.newstoss.portfolio.application.port.out.CreatePortfolioPort;
import com.newstoss.portfolio.application.port.out.LoadPortfolioPort;
import com.newstoss.portfolio.entity.Portfolio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class PortfolioAdapter implements CreatePortfolioPort, LoadPortfolioPort {

    private final JpaPortfolioRepository repository;


    @Override
    public void create(Portfolio portfolio) {
        repository.save(portfolio);
    }


    @Override
    public Portfolio loadPortfolio(UUID memberId) {
        return repository.findByMemberId(memberId).orElseThrow(
                () -> new CustomException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND)
        );
    }
}
