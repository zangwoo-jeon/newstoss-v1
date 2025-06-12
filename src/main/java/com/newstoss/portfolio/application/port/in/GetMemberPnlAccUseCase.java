package com.newstoss.portfolio.application.port.in;

import java.util.UUID;

public interface GetMemberPnlAccUseCase {
    /**
     * 특정 사용자의 누적 손익을 조회한다.
     * @param memberId 사용자 ID
     * @param period M 또는 total 로 지정된 기간
     * @return 누적 손익 정보
     */
    Long getMemberPnlAcc(UUID memberId, String period);
}
