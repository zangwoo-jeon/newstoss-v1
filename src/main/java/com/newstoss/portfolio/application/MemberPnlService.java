package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.MemberPnlErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.MemberPnlPeriodResponseDto;
import com.newstoss.portfolio.application.port.in.GetMemberPnlAccUseCase;
import com.newstoss.portfolio.application.port.in.GetMemberPnlPeriodUseCase;
import com.newstoss.portfolio.application.port.in.UpdateMemberPnlUseCase;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPeriodPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.PortfolioStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberPnlService implements GetMemberPnlPeriodUseCase , GetMemberPnlAccUseCase , UpdateMemberPnlUseCase {

    private final GetMemberPnlPeriodPort getMemberPnlPeriodPort;
    private final GetMemberPnlPort getMemberPnlPort;
    private final GetPortfolioStocksPort getPortfolioStocksPort;

    @Override
    public MemberPnlPeriodResponseDto getMemberPnlPeriod(UUID memberId, String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(period, endDate);

        if (startDate == null) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_PERIOD_ERROR);
        }

        List<MemberPnl> memberPnlList = getMemberPnlPeriodPort.getMemberPnlDaily(memberId, startDate, endDate);

        MemberPnl today = getMemberPnlPort.getMemberPnl(memberId, endDate).orElseThrow(
                () -> new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND)
        );
        Long prevAsset = getMemberPnlPort.getMemberPnl(memberId, startDate)
                .map(MemberPnl::getAsset)
                .orElse(1L);

        return new MemberPnlPeriodResponseDto(
                today.getAsset(),
                today.getPnl(),
                memberPnlList,
                today.getAsset()- prevAsset,
                ((double)(today.getAsset()-prevAsset)/prevAsset * 100)
        );
    }

    private LocalDate calculateStartDate(String period, LocalDate endDate) {
        return switch (period) {
            case "D" -> endDate.minusDays(1);
            case "W" -> endDate.minusDays(7);
            case "M" -> endDate.minusMonths(1);
            case "3M" -> endDate.minusMonths(3);
            case "Y" -> endDate.minusYears(1);
            default -> null;
        };
    }

    @Override
    public Long getMemberPnlAcc(UUID memberId, String period) {
        LocalDate endDate = LocalDate.now();
        if (period.equals("Today")) {
            LocalDate startDate = endDate.minusDays(1);
            return getMemberPnlPort.getMemberPnlAcc(memberId,startDate,endDate);
        } else if (period.equals("M")) {
            LocalDate startDate = endDate.withDayOfMonth(1);
            return getMemberPnlPort.getMemberPnlAcc(memberId, startDate, endDate);
        } else {
            return getMemberPnlPort.getMemberPnlAcc(memberId);
        }
    }

    @Override
    public void updateTodayPnl(UUID memberId) {
        MemberPnl todayPnl = getMemberPnlPort.getMemberPnl(memberId, LocalDate.now()).orElseThrow(
                () -> new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND)
        );
        Optional<MemberPnl> yesterDayPnl = getMemberPnlPort.getMemberPnl(memberId, LocalDate.now().minusDays(1));
        Long yesterDayAsset;
        if (yesterDayPnl.isEmpty()) {
            yesterDayAsset = 0L;
        } else {
            yesterDayAsset = yesterDayPnl.get().getAsset();
        }
        List<PortfolioStock> portfolioStocks = getPortfolioStocksPort.getPortfolioStocks(memberId);
        long asset = 0L;
        for (PortfolioStock portfolioStock : portfolioStocks) {
            asset += (long)portfolioStock.getEntryPrice() * portfolioStock.getStockCount();
        }
        todayPnl.updateAsset(asset);
        todayPnl.initPnl(asset - yesterDayAsset);
    }
}

