package com.newstoss.global.handler;

import com.newstoss.global.response.ResponseErrorEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.class)
    public void handleHttpServerError(HttpServerErrorException ex) {
        log.warn("KIS API 초과 요청 감지 - 상태: {}, 메시지: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
    }

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

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Void> handleIOException(IOException e) throws IOException {
        // Broken pipe 예외면서, stack trace에 SseEmitter 포함 여부로 필터링
        if (isSseException(e) || e.getMessage().contains("Broken pipe")) {
            log.warn("🌐 SSE 연결 중 IOException 처리됨: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // SSE용
        }

        // 그 외 IOException은 다시 던져서 Spring/Tomcat이 처리하게
        throw e;
    }

    private boolean isSseException(IOException e) {
        for (StackTraceElement element : e.getStackTrace()) {
            if (element.getClassName().contains("SseEmitter")) {
                return true;
            }
        }
        return false;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorEntity> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("요청값이 유효하지 않습니다.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseErrorEntity.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .isSuccess(false)
                        .name("ValidationException")
                        .code("VALIDATION_ERROR")
                        .message(message) // ✅ 여기에 첫 번째 오류 메시지만 전달
                        .build());
    }
}
