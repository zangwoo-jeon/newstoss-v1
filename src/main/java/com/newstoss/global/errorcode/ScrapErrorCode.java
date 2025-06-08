package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ScrapErrorCode implements ErrorCode{
    DUPLICATE_SCRAP(HttpStatus.BAD_REQUEST, false, "SCRAP-001", "이미 스크랩한 뉴스입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "SCRAP-001", "이미 스크랩한 뉴스입니다."),
    NEWS_NOT_FOUND(HttpStatus.NOT_FOUND, false, "SCRAP-001", "이미 스크랩한 뉴스입니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}
