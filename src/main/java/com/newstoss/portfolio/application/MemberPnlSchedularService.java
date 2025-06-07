package com.newstoss.portfolio.application;

import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.application.out.MemberQueryPort;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.application.port.out.GetMemberPnlPort;
import com.newstoss.portfolio.entity.MemberPnl;
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
public class MemberPnlSchedularService {

    private final MemberQueryPort memberQueryPort;
    private final GetMemberPnlPort getMemberPnlPort;

    @Scheduled(cron = "0 1 0 * * *")
    public void createDailyPnl() {
        log.info("Creating Daily Pnl");

        List<Member> allMembers = memberQueryPort.findAll();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        for (Member member : allMembers) {
            MemberPnl yesterdayPnl = getMemberPnlPort.getMemberPnl(member.getMemberId(), yesterday).orElseThrow(
                    () -> new CustomException(PortfolioErrorCode.PNL_NOT_FOUND)
            );
            MemberPnl todayPnl = MemberPnl.createMemberPnl(member.getMemberId(), 0, today, yesterdayPnl.getAsset());

        }
    }
}
