package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RedisStreamErrorCode implements ErrorCode {

    REDIS_CONSUMER_ERROR_CODE(HttpStatus.BAD_REQUEST, false, "REDIS-STREAM-001", "Redis consumer 에러입니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}
