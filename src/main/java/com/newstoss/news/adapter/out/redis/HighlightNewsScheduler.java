package com.newstoss.news.adapter.out.redis;

import com.newstoss.news.application.redis.impl.HighlightNewsCacheService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("prod")
public class HighlightNewsScheduler {

    private final HighlightNewsCacheService highlightNewsCacheService;

    @PostConstruct
    public void init() {
        log.info("✅ HighlightNewsScheduler 등록됨 (@Profile=prod)");
    }

    @Scheduled(cron = "0 * 9-17 * * *") // 매일 00:00
    public void updateHighlightNews() {

        highlightNewsCacheService.forceUpdateHighlightNewsCacheTest();
        log.info("🗓️ 하이라이트 뉴스 캐시 갱신 완료");
    }
}
