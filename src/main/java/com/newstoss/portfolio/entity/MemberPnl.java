package com.newstoss.portfolio.entity;

import com.newstoss.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "member_pnl")
public class MemberPnl extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    private Long Pnl;

    @Column(name = "pnl_date")
    private LocalDate date;

    private Long asset;


    //== 생성 메서드 ==//
    public static MemberPnl createMemberPnl(UUID memberId, Long pnl, LocalDate date, Long asset) {
        MemberPnl memberPnl = new MemberPnl();
        memberPnl.memberId = memberId;
        memberPnl.Pnl = pnl;
        memberPnl.date = date;
        memberPnl.asset = asset;
        return memberPnl;
    }
    //== 비즈니스 로직 ==//

    public void updateAsset(Long asset) {
        this.asset += asset;
    }

    public void initAsset(Long asset) {
        this.asset = asset;
    }
    public void initPnl(Long pnl) {
        this.Pnl = pnl;
    }
}
