package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.entity.MemberPnl;

import java.time.LocalDate;
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
     * @param start 시작 날짜
     * @param end 종료 날짜
     * @return 일별 PnL 리스트
     */
    Long getMemberPnlAcc(UUID memberId, LocalDate start, LocalDate end);
    /**
     * 특정 사용자의 누적 손익을 조회한다.
     * @param memberId 사용자 ID
     * @return 누적 손익 정보
     */
    Long getMemberPnlAcc(UUID memberId);
}
