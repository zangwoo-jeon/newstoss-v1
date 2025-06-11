package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RedisAndSseErrorCode implements ErrorCode {
    SSE_NO_CONNECTED_CLIENT(HttpStatus.BAD_REQUEST, false, "SSE-001", "연결된 클라이언트가 없습니다."),
    SSE_SEND_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, false, "SSE-002", "SSE 데이터 전송 중 오류가 발생했습니다."),
    SSE_CONNECTED_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, false, "SSE-003", "SSE 연결 실패");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}