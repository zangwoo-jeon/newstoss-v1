package com.newstoss.news.application.news.service;

import com.newstoss.global.jwt.JwtProvider;
import com.newstoss.global.jwt.JwtResolver;
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

    private final JwtResolver jwtResolver;

    public void recordNewsLog(String newsId, HttpServletRequest request) {
        try {
            UUID memberId = jwtResolver.extractMemberId(request);
            if (memberId != null) {
                log.info("[memberId : {}] [newsId : {}]", memberId, newsId);
            } else {
                log.info("[memberId : anonymous] [newsId : {}]", newsId);
            }
        } catch (Exception e) {
            log.info("[memberId : anonymous] [newsId : {}]", newsId);
        }
    }

} 