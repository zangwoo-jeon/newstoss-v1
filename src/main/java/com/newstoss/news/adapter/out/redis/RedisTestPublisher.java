//package com.newstoss.global.redis;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class RedisTestPublisher implements CommandLineRunner {
//
//    private final StringRedisTemplate redisTemplate;
//
//    @Override
//    public void run(String... args) {
//        String json = """
//            {
//              "news_id": "test_0001",
//              "wdate": "2025-06-05T15:00:00",
//              "title": "Redis 테스트 뉴스",
//              "article": "Redis CLI 없이 Spring으로 보내는 테스트 뉴스입니다.",
//              "url": "http://example.com/news/1",
//              "press": "테스트신문",
//              "image": "http://example.com/image.jpg"
//            }
//            """;
//
//        redisTemplate.convertAndSend("news-channel", json);
//        System.out.println("Redis에 테스트 메시지 발행 완료");
//    }
//}
