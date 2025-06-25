package com.newstoss.news.adapter.out.redis.realtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RealTimeNewsCache {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String KEY = "Real-Time-News";
    private static final Duration TTL = Duration.ofHours(24);


}
