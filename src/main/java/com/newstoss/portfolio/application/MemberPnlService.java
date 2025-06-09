package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.MemberPnlErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.MemberPnlPeriodResponseDto;
import com.newstoss.portfolio.application.port.in.GetMemberPnlAccUseCase;
import com.newstoss.portfolio.application.port.in.GetMemberPnlPeriodUseCase;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPeriodPort;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.entity.MemberPnl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberPnlService implements GetMemberPnlPeriodUseCase , GetMemberPnlAccUseCase {

    private final GetMemberPnlPeriodPort getMemberPnlPeriodPort;
    private final GetMemberPnlPort getMemberPnlPort;

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
        MemberPnl periodAgo = getMemberPnlPort.getMemberPnl(memberId, startDate).orElseThrow(
                () -> new CustomException(MemberPnlErrorCode.MEMBER_PNL_NOT_FOUND)
        );

        return new MemberPnlPeriodResponseDto(
                today.getAsset(),
                today.getPnl(),
                memberPnlList,
                today.getAsset()- periodAgo.getAsset(),
                ((double)(today.getAsset()-periodAgo.getAsset())/periodAgo.getPnl() * 100)
        );
    }

    private LocalDate calculateStartDate(String period, LocalDate endDate) {
        return switch (period) {
            case "D" -> endDate.minusDays(7);
            case "M" -> endDate.minusMonths(1);
            case "3M" -> endDate.minusMonths(3);
            case "Y" -> endDate.minusYears(1);
            default -> null;
        };
    }

    @Override
    public Long getMemberPnlAcc(UUID memberId, String period) {
        LocalDate endDate = LocalDate.now();
        if (period.equals("M")) {
            LocalDate startDate = endDate.withDayOfMonth(1);
            return getMemberPnlPort.getMemberPnlAcc(memberId, startDate, endDate);
        } else {
            return getMemberPnlPort.getMemberPnlAcc(memberId);
        }
    }

}

