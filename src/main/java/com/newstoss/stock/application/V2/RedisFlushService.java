//package com.newstoss.stock.application.V2;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
//import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
//import com.newstoss.stock.entity.Stock;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class RedisFlushService {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final ObjectMapper objectMapper;
//    private final LoadStockPort loadStockPort;
//
//    @Scheduled(fixedRate = 60_000)
//    public void flushStockCacheToDb() {
//        Set<String> keys = redisTemplate.keys("stock:*");
//        if (keys.isEmpty()) return;
//
//        for (String key : keys) {
//            String stockCode = key.split(":")[1];
//            Object object = redisTemplate.opsForValue().get(key);
//            KisStockDto kisStockDto = objectMapper.convertValue(object, KisStockDto.class);
//
//            Stock stock = loadStockPort.LoadStockByStockCode(stockCode);
//            stock.updateStockPrice(kisStockDto.getPrice(),kisStockDto.getChangeAmount(),kisStockDto.getSign(),kisStockDto.getChangeRate());
//
//            redisTemplate.delete(key);
//            log.info("DB 반영 및 캐시 삭제 완료 - {}", stockCode);
//        }
//    }
//}
