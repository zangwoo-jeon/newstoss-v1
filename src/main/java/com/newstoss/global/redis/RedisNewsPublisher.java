package com.newstoss.global.redis;

import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisNewsPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic newsTopic;

    public void publish(MLNewsDTO news){
        redisTemplate.convertAndSend(newsTopic.getTopic(), news);
    }
}
