package com.newstoss.global.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogWarmupScheduler {

    @Scheduled(cron = "1 0 0 * * *")
    public void touchDailyLog() {
        log.info("Log file rollover trigger");
    }
}
