package com.newstoss.global.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "USER-001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_ACCOUNT(HttpStatus.BAD_REQUEST, false, "USER-002", "이미 존재하는 계정입니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, false, "USER-003", "입력한 아이디를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, false, "USER-004", "비밀번호가 일치하지 않습니다."),
    MEMBERID_NOT_FOUND(HttpStatus.NOT_FOUND, false, "USER-005", "사용자 Id를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

}
