package com.newstoss.global.kis;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class TokenSchedular {
    private final KisTokenManager kisTokenManager;

    @PostConstruct
    public void init() {
        // 초기화 시 토큰 갱신
        kisTokenManager.getToken();
    }

    @Scheduled(fixedDelay = 1000*60*60*12, initialDelay = 1000*60*60*12) // 12시간마다 갱신
    public void refreshToken() {
        try {
            kisTokenManager.refresh();
        } catch (Exception e) {
            // 예외 처리 로직 추가
            System.out.println("토큰 갱신 실패: " + e.getMessage());
        }
    }
}
