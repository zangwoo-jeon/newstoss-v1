package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NewsErrorCode implements ErrorCode {
    NEWS_NOT_FOUND(HttpStatus.NOT_FOUND, false, "NEWS-001", "뉴스를 찾을 수 없습니다."),
    ML_SERVER_ERROR(HttpStatus.BAD_GATEWAY, false, "ML-001", "ML 서버와 통신에 실패했습니다.");


    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

}