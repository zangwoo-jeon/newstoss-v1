package com.newstoss.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SecurityContextStrategyConfig {

    @PostConstruct
    public void setSecurityContextStrategy() {
        // 자식 스레드에서도 인증 정보 공유되도록 설정
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
