package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.MemberPnlErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.domain.event.MemberSignUpEvent;
import com.newstoss.portfolio.adapter.inbound.web.dto.MemberPnlDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.MemberPnlPeriodResponseDto;
import com.newstoss.portfolio.application.port.in.GetMemberPnlAccUseCase;
import com.newstoss.portfolio.application.port.in.GetMemberPnlPeriodUseCase;
import com.newstoss.portfolio.application.port.out.CreateMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPeriodPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.application.port.out.GetPortfolioStocksPort;
import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.PortfolioStock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberPnlService implements GetMemberPnlPeriodUseCase , GetMemberPnlAccUseCase {

    private final GetMemberPnlPeriodPort getMemberPnlPeriodPort;
    private final GetMemberPnlPort getMemberPnlPort;
    private final GetPortfolioStocksPort getPortfolioStocksPort;
    private final CreateMemberPnlPort createMemberPnlPort;

    @Override
    public MemberPnlPeriodResponseDto getMemberPnlPeriod(UUID memberId, String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(period, endDate);

        if (startDate == null) {
            throw new CustomException(MemberPnlErrorCode.MEMBER_PNL_PERIOD_ERROR);
        }

        List<MemberPnl> memberPnlList = getMemberPnlPeriodPort.getMemberPnlDaily(memberId, startDate, endDate);
        List<MemberPnlDto> list = memberPnlList.stream()
                .map(p -> new MemberPnlDto(
                        p.getDate(),
                        p.getPnl(),
                        p.getAsset()
                )).toList();

        Long prevAsset = getMemberPnlPort.getMemberPnl(memberId, startDate)
                .map(MemberPnl::getAsset)
                .orElse(0L);

        return new MemberPnlPeriodResponseDto(
                list,
                prevAsset
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
        List<PortfolioStock> portfolioStocks = getPortfolioStocksPort.getPortfolioStocks(memberId);
        if (period.equals("Today")) return getMemberPnlPort.todayMemerPnlAcc(memberId,portfolioStocks);
        else if (period.equals("M")) return getMemberPnlPort.monthlyMemberPnlAcc(memberId);
        else return getMemberPnlPort.totalMemberPnlAcc(memberId);
    }



    @EventListener
    public void createMemberPnl(MemberSignUpEvent event) {
        UUID memberId = event.memberId();
        createMemberPnlPort.create(MemberPnl.createMemberPnl(memberId, 0L, LocalDate.now(), 0L));
    }
}

