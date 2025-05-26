package com.newstoss.global.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
    boolean isSuccess();
    String getCode();
    String getMessage();
}