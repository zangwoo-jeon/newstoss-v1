package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NewsErrorCode implements ErrorCode {
    ML_COMMUNICATION_FAIL(HttpStatus.BAD_GATEWAY, false, "ML-001", "ML 서버와 통신에 실패했습니다."),
    ML_INVALID_RESPONSE(HttpStatus.BAD_GATEWAY, false, "ML-002", "ML 서버 응답 포맷이 잘못되었습니다."),
    ML_EMPTY_RESULT(HttpStatus.NOT_FOUND, false, "ML-003", "ML 서버에서 유사 뉴스가 조회되지 않았습니다."),
    ML_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, false, "ML-004", "ML 서버 응답 시간이 초과되었습니다."),
    ML_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, false, "ML-005", "ML 서버 인증이 실패했습니다."),
    ML_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "ML-999", "알 수 없는 오류가 발생했습니다."),
    NEWS_NOT_FOUND(HttpStatus.NOT_FOUND, false, "NEWS-001", "뉴스를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

}