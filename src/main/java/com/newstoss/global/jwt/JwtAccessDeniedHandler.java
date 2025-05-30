package com.newstoss.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.JwtErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(JwtErrorCode.ACCESS_DENIED.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("success", JwtErrorCode.ACCESS_DENIED.isSuccess());
        body.put("code", JwtErrorCode.ACCESS_DENIED.getCode());
        body.put("message", JwtErrorCode.ACCESS_DENIED.getMessage());

        new ObjectMapper().writeValue(response.getWriter(), body);
    }
}
