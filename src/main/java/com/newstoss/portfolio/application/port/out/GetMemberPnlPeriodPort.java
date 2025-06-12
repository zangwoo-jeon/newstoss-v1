package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.entity.MemberPnl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GetMemberPnlPeriodPort {
    /**
     * 회원의 일일 손익 정보를 조회한다.
     * @param memberId 회원 ID
     * @param start 시작 날짜 (YYYY-MM-DD 형식)
     * @param end 종료 날짜 (YYYY-MM-DD 형식)
     * @return 일일 손익 정보 목록
     */
    List<MemberPnl> getMemberPnlDaily(UUID memberId, LocalDate start, LocalDate end);
}
