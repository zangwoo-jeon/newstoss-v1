package com.newstoss.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberPnl {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    private Integer Pnl;

    @Column(name = "pnl_date")
    private LocalDate date;

    private Long asset;

    private Long changeAmount;

    //== 생성 메서드 ==//
    public static MemberPnl createMemberPnl(UUID memberId, Integer pnl, LocalDate date, Long asset, Long changeAmount) {
        MemberPnl memberPnl = new MemberPnl();
        memberPnl.memberId = memberId;
        memberPnl.Pnl = pnl;
        memberPnl.date = date;
        memberPnl.asset = asset;
        memberPnl.changeAmount = changeAmount;
        return memberPnl;
    }
    //== 비즈니스 로직 ==//
    public void updatePnl(Integer pnl, Long asset) {
        this.Pnl = pnl;
        this.asset = asset;
    }
}
