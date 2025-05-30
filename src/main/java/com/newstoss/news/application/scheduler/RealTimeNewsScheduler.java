package com.newstoss.news.application.scheduler;

import com.newstoss.global.redis.RedisNewsPublisher;
import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import com.newstoss.news.application.port.out.MLNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RealTimeNewsScheduler {
    private final MLNewsPort mlNewsPort;
    private final RedisNewsPublisher redisNewsPublisher;

    @Scheduled(fixedDelay = 10000)
    public void fetchAndPublish() {
        List<MLNewsDTO> news = mlNewsPort.getRealTimeNews();
        news.forEach(redisNewsPublisher::publish);
    }
}
