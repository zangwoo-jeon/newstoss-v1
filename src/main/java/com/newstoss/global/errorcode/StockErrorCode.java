package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StockErrorCode implements ErrorCode {
    KIS_NULL_CODE(HttpStatus.BAD_REQUEST, false, "KIS-001", "KIS 응답이 null입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, false, "KIS-002", "카테고리를 찾을 수 없습니다."),
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, false, "KIS-003", "해당 주식을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

}
