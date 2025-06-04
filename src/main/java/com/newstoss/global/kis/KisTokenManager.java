package com.newstoss.global.kis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KisTokenManager {
    private final KisTokenClient kisTokenClient;
    private final KisTokenRepository kisTokenRepository;
    private String token;
    private LocalDateTime expireAt;

    public synchronized String refresh() {
        log.info("KisTokenManager.refresh() called");
        Optional<KisToken> findToken = kisTokenRepository.findTopByOrderByIdDesc();
        if (findToken.isPresent()) {
            KisToken existingToken = findToken.get();
            token = existingToken.getToken();
            expireAt = existingToken.getExpireAt();

            if (!isTokenExpired()) {
                log.info("KisTokenManager.refresh() - Existing token is still valid, returning existing token");
                return token;
            }
        }
        KisTokenResponse tokenResponse = kisTokenClient.fetchToken();
        KisToken newToken = KisToken.createToken(tokenResponse);
        kisTokenRepository.save(newToken);

        token = newToken.getToken();
        expireAt = newToken.getExpireAt();
        return newToken.getToken();
    }
    // 토큰을 가져오는 메서드
    public synchronized String getToken() {
        if (token == null || isTokenExpired()) {
            log.info("KisTokenManager.getToken() called - Token is null or expired, refreshing token");
            refresh();
        } else {
            log.info("KisTokenManager.getToken() called - Returning existing token");
        }
        return token;
    }
    private boolean isTokenExpired() {
        if (expireAt == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(expireAt.minusSeconds(30)); // 30초 여유
    }
}

