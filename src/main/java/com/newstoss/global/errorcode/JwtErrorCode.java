package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements ErrorCode {
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, false, "JWT-001", "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, false, "JWT-002", "토큰이 유효하지 않습니다."),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, false, "JWT-003", "토큰이 존재하지 않습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, false, "JWT-004", "지원하지 않는 JWT 형식입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, false, "JWT-005", "JWT 구조가 잘못되었습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, false, "JWT-006", "인증에 실패했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, false, "JWT-007", "접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}
