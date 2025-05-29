package com.newstoss.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.JwtErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(JwtErrorCode.AUTHENTICATION_FAILED.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("success", JwtErrorCode.AUTHENTICATION_FAILED.isSuccess());
        body.put("code", JwtErrorCode.AUTHENTICATION_FAILED.getCode());
        body.put("message", JwtErrorCode.AUTHENTICATION_FAILED.getMessage());

        new ObjectMapper().writeValue(response.getWriter(), body);
    }
}
