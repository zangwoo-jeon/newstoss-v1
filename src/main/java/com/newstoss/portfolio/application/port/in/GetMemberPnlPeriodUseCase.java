package com.newstoss.portfolio.application.port.in;

import com.newstoss.portfolio.adapter.inbound.web.dto.response.MemberPnlPeriodResponseDto;
import com.newstoss.portfolio.entity.MemberPnl;

import java.util.List;
import java.util.UUID;

public interface GetMemberPnlPeriodUseCase {
    /**
     * 특정 기간 동안의 손익을 조회한다.
     * @param memberId 사용자 ID
     * @param period 일별, 월별 등의 기간 정보
     * @return 손익 정보
     */
    MemberPnlPeriodResponseDto getMemberPnlPeriod(UUID memberId, String period);
}
