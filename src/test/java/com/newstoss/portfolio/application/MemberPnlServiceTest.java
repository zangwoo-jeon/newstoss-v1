package com.newstoss.portfolio.application;

import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.MemberPnlPeriodResponseDto;
import com.newstoss.portfolio.entity.MemberPnl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberPnlServiceTest {

    @Autowired
    MemberPnlService service;

    @Autowired
    JpaMemberRepository repository;

    @Test
    public void memberPnlServiceTest() {
        //given
        List<Member> members = repository.findByName("test");
        UUID memberId = members.get(0).getMemberId();
        //when
        MemberPnlPeriodResponseDto dto = service.getMemberPnlPeriod(memberId, "M");

        //then
        System.out.println("dto.getTodayAsset() = " + dto.getTodayAsset());
        System.out.println("dto.getTodayPnl() = " + dto.getTodayPnl());
        System.out.println("한달 전과 비교한 금액 " + dto.getPeriodPnl());
        System.out.println("dto.getPnlPercent() = " + dto.getPnlPercent());
        List<MemberPnl> pnlHistory = dto.getPnlHistory();
        for (MemberPnl memberPnl : pnlHistory) {
            System.out.println("memberPnl.getDate() = " + memberPnl.getDate());
            System.out.println("memberPnl = " + memberPnl.getPnl());
            System.out.println("memberPnl.getAsset() = " + memberPnl.getAsset());
        }

    }

}