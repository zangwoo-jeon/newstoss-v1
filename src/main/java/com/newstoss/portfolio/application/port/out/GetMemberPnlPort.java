package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.entity.MemberPnl;
import com.newstoss.portfolio.entity.PortfolioStock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetMemberPnlPort {
    /**
     * 특정한 날의 회원의 손익 정보를 조회한다.
     * @param memberId 회원 ID
     * @param date 조회할 날짜 (YYYY-MM-DD 형식)
     * @return 회원의 손익 정보
     */
    Optional<MemberPnl> getMemberPnl(UUID memberId, LocalDate date);
    /**
     * 특정 회원의 달별 PnL을 조회한다.
     * @param memberId 회원 ID
     * @return 월별 PnL 리스트
     */
    Long monthlyMemberPnlAcc(UUID memberId);
    Long todayMemerPnlAcc(UUID memberId, List<PortfolioStock> stocks);
    /**
     * 특정 사용자의 누적 손익을 조회한다.
     * @param memberId 사용자 ID
     * @return 누적 손익 정보
     */
    Long totalMemberPnlAcc(UUID memberId);
}
