package com.newstoss.global.jwt;

import com.newstoss.global.errorcode.JwtErrorCode;
import com.newstoss.global.handler.CustomException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtResolver {

    private final JwtProvider jwtProvider;

    public String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public UUID extractMemberId(HttpServletRequest request) {
        String token = extractTokenFromCookie(request);
        if (token == null) return null;
        if (!jwtProvider.validateToken(token)) throw new CustomException(JwtErrorCode.INVALID_TOKEN);
        return jwtProvider.getMemberId(token);
    }
}
