package com.newstoss.news.application.news.service;

import com.newstoss.global.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsLogRecordService {
    private static final Logger log = LoggerFactory.getLogger("com.newstoss.news.application.news.v2.impl.ml");

    private final JwtProvider jwtProvider;

    public void recordNewsLog(String newsId, HttpServletRequest request) {
        try {
            String token = extractTokenFromCookie(request);
            if (token != null && jwtProvider.validateToken(token)) {
                UUID memberId = jwtProvider.getMemberId(token);
                log.info("[memberId : {}] [newsId : {}]", memberId, newsId);
            } else {
                log.info("[memberId : anonymous] [newsId : {}]", newsId);
            }
        } catch (Exception e) {
            log.info("[memberId : anonymous] [newsId : {}]", newsId);
        }
    }

    // 쿠키에서 토큰 추출하는 헬퍼 메서드
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
} 