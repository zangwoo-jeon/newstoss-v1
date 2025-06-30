package com.newstoss.global.errorcode;

import com.newstoss.global.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode implements ErrorCode {
    
    ALREADY_EXISTS_FAVORITE_STOCK(HttpStatus.BAD_REQUEST, false, "FAVORITE-001", "이미 등록된 종목입니다."),
    FAVORITE_STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, false, "FAVORITE-002", "관심 종목을 찾을 수 없습니다."),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "FAVORITE-003", "외부 API 호출 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
} 