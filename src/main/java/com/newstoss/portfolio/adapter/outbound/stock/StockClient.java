package com.newstoss.portfolio.adapter.outbound.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.portfolio.adapter.inbound.web.dto.redis.StockDto;
import com.newstoss.portfolio.application.port.in.GetStockInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockClient implements GetStockInfo {

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public StockDto stockInfo(String stockCode) {
            // Redis 확인
        Object object = redisTemplate.opsForValue().get("STOCK:" + stockCode);
        if (object != null) {
            // Redis에 있으면 캐시된 데이터 반환
            log.info("Cache hit for stock code: {}", stockCode);
            return objectMapper.convertValue(object, StockDto.class);
        }
            // 없으면 API 요청
        return callStockApi(stockCode);
    }
    private StockDto callStockApi(String stockCode) {
        String url = "http://43.201.62.55:8080/api/v2/stocks/info/" + stockCode;
        try {
            return restTemplate.getForObject(url, StockDto.class);
        } catch (Exception e) {
            log.error("API Error for stock code: {}", stockCode, e);
            return null;
        }
    }
}
