package com.newstoss.member.application.in.query;

import com.newstoss.member.adapter.in.web.dto.response.MemberInfoDto;

import java.util.UUID;

public interface GetMemberInfoUseCase {
    /**
     * 유저 정보를 조회하는 메서드
     * @param memberId 멤버 id
     * @return 유저 이름, 유저 오늘 pnl , 유저가 가지고 있는 주식 정보
     */
    MemberInfoDto getMemberInfo(UUID memberId);
}
