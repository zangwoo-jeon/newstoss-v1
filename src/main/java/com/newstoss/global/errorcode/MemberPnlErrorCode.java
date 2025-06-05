package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberPnlErrorCode implements ErrorCode {
    MEMBER_PNL_PERIOD_ERROR(HttpStatus.BAD_REQUEST, false, "MEMBER-PNL-001", "멤버 PnL 기간 조회에 실패했습니다."),
    MEMBER_PNL_NOT_FOUND(HttpStatus.NOT_FOUND, false, "MEMBER-PNL-000", "해당 멤버의 PnL 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}
