package com.newstoss.portfolio.adapter.outbound.persistence.repository;

import com.newstoss.portfolio.entity.MemberPnl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPAMemberPnlRepository extends JpaRepository<MemberPnl, Long> {
    /**
     * 특정 회원의 일별 PnL을 조회하는 메서드
     *
     * @param memberId 회원 ID
     * @param start 시작 날짜
     * @param end 종료 날짜
     * @return 일별 PnL 리스트
     */
    List<MemberPnl> findByMemberIdAndDateBetween(UUID memberId, LocalDate start, LocalDate end);
    /**
     * 특정 회원의 특정 날짜에 대한 PnL을 조회하는 메서드
     *
     * @param memberId 회원 ID
     * @param date 조회할 날짜
     * @return 해당 날짜의 PnL 정보
     */
    Optional<MemberPnl> findByMemberIdAndDate(UUID memberId,LocalDate date);
    /**
     * 특정 회원의 모든 PnL 정보를 조회하는 메서드
     *
     * @param memberId 회원 ID
     * @return 해당 회원의 모든 PnL 정보 리스트
     */
    List<MemberPnl> findByMemberId(UUID memberId);
    /**
     * 특정 회원의 특정 기간 동안의 누적 PnL을 조회하는 메서드
     *
     * @param memberId 회원 ID
     * @param start 시작 날짜
     * @param end 종료 날짜
     * @return 해당 기간 동안의 누적 PnL
     */
    @Query("SELECT SUM(p.Pnl) FROM MemberPnl p WHERE p.memberId = :memberId AND p.date BETWEEN :start AND :end")
    Long findAccByMemberIdAndDateBetween(UUID memberId, LocalDate start, LocalDate end);
    /**
     * 특정 회원의 누적 PnL을 조회하는 메서드
     *
     * @param memberId 회원 ID
     * @return 해당 회원의 누적 PnL
     */
    @Query("SELECT SUM(p.Pnl) FROM MemberPnl p WHERE p.memberId = :memberId")
    Long findAccByMemberId(UUID memberId);
}
