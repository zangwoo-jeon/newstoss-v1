package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PortfolioErrorCode implements ErrorCode {
    PNL_NOT_FOUND(HttpStatus.NOT_FOUND,false,"PNL-000","PNL 데이터를 찾을 수 없습니다."),
    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, false, "PORTFOLIO-000", "포트폴리오를 찾을 수 없습니다."),
    PORTFOLIO_STOCK_QUANTITY_NOT_ENOUGH(HttpStatus.BAD_REQUEST, false, "PORTFOLIO-001", "포트폴리오에 보유한 주식의 수량이 부족합니다."),
    PORTFOLIO_MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, false, "PORTFOLIO-004", "이미 포트폴리오에 존재하는 멤버입니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}
