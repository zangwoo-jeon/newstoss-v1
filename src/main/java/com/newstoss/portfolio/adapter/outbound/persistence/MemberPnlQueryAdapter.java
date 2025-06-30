package com.newstoss.portfolio.adapter.outbound.persistence;

import com.newstoss.global.errorcode.MemberPnlErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.inbound.web.dto.redis.StockDto;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JPAMemberPnlRepository;
import com.newstoss.portfolio.adapter.outbound.stock.StockClient;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPeriodPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.PortfolioStock;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberPnlQueryAdapter implements GetMemberPnlPeriodPort , GetMemberPnlPort {

    private final JPAMemberPnlRepository repository;
    private final StockClient stockClient;

    @Override
    public List<MemberPnl> getMemberPnlDaily(UUID memberId, LocalDate start, LocalDate end) {
        List<MemberPnl> byMemberIdAndDateBetween = repository.findByMemberIdAndDateBetween(memberId, start, end);
        if (byMemberIdAndDateBetween.isEmpty()) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND);
        }
        return byMemberIdAndDateBetween;
    }

    @Override
    public Optional<MemberPnl> getMemberPnl(UUID memberId, LocalDate date) {
        return repository.findByMemberIdAndDate(memberId, date);
    }

    @Override
    public Long todayMemerPnlAcc(UUID memberId, List<PortfolioStock> stocks) {
        LocalDate today = LocalDate.now();
        long acc = 0L;
        for (PortfolioStock stock : stocks) {
            StockDto stockDto = stockClient.stockInfo(stock.getStockCode());
            int price = Integer.parseInt(stockDto.getPrice());
            int stockCount = stock.getStockCount();

            if (stock.getCreatedDate().toLocalDate().isEqual(today)) {
                // 오늘 산 종목
                int entryPrice = stock.getEntryPrice();
                acc += (long) (price - entryPrice) * stockCount;
            } else {
                // 어제도 보유한 종목 → 오늘 변동분만 반영
                int changeAmount = Integer.parseInt(stockDto.getChangeAmount());
                acc += (long) changeAmount * stockCount;
            }
        }
        return acc;
    }

    @Override
    public Long monthlyMemberPnlAcc(UUID memberId) {
        LocalDate end = LocalDate.now().minusDays(1);
        LocalDate start = end.withDayOfMonth(1);
        Long acc = repository.findAccByMemberIdAndDateBetween(memberId, start, end);
        if (acc == null) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND);
        }
        return acc;
    }

    @Override
    public Long totalMemberPnlAcc(UUID memberId) {
        Long accByMemberId = repository.findAccByMemberId(memberId);
        if (accByMemberId == null) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND);
        }
        return accByMemberId;
    }

}
