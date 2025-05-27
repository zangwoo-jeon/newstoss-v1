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

    public String refresh() {
        log.info("KisTokenManager.refresh() called");
        KisTokenResponse tokenResponse = kisTokenClient.fetchToken();
        KisToken newToken = KisToken.createToken(tokenResponse);
        kisTokenRepository.save(newToken);
        return newToken.getToken();
    }

    public String getToken() {
        Optional<KisToken> latest = kisTokenRepository.findTopByOrderByIdDesc();

        if (latest.isEmpty()) {
            return refresh();
        } else if (latest.get().getExpireAt().isBefore(LocalDateTime.now())) {
            log.info("KisTokenManager.getToken() - Token expired, refreshing token");
            KisTokenResponse tokenResponse = kisTokenClient.fetchToken();
            return latest.get().changeToken(tokenResponse);
        }
        else {
            log.info("KisTokenManager.getToken() - Returning existing token");
            return latest.get().getToken();
        }
    }
}
