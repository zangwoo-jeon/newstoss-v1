package com.newstoss.global.handler;

import com.newstoss.global.response.ResponseErrorEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseErrorEntity> handleCustomException(CustomException e) {
        log.warn("[EXCEPTION] code={}, message={}", e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        return ResponseErrorEntity.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseErrorEntity> handleException(Exception e) {
        log.error("[UnexpectedException] message={}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseErrorEntity.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .isSuccess(false)
                        .name("InternalServerError")
                        .code("INTERNAL_SERVER_ERROR")
                        .message("알수 없는 에러가 발생했습니다..")
                        .build());
    }
}
