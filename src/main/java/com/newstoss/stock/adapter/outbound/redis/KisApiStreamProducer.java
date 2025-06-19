package com.newstoss.stock.adapter.outbound.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.kis.dto.KisApiRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KisApiStreamProducer {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final long DEDUP_TTL = 30;

    /**
     * 주식 현재가를 저장하는 메서드
     * @param stockCode 주식 코드
     */
    public void sendStockRequest(String stockCode) {
        String dedupKey = "stream-dedup:stock:" + stockCode;

        // setIfAbsent는 key가 없으면 true, 있으면 false 반환
        Boolean isFirst = redisTemplate.opsForValue().setIfAbsent(dedupKey, "1", DEDUP_TTL, java.util.concurrent.TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(isFirst)) {
            log.info("메세지 적재 성공 : 주식 코드 {}", stockCode);
            KisApiRequestDto dto = new KisApiRequestDto("stock", Map.of("stockCode", stockCode));
            Map<String, Object> map = new ObjectMapper().convertValue(dto, new TypeReference<>() {});
            redisTemplate.opsForStream().add("kis-api-request", map);
        } else {
            log.info("주식이 이미 호출되었습니다. 주식 코드  : {}", stockCode);
        }
    }

    /**
     * 환율 Fx 정보를 Redis Stream에 producing 하는 메서드
     * @param FxType Fx 맵에서 타입값
     * @param FxCode Fx 맵에서 코드값
     */
    public void sendFxRequest(String FxType, String FxCode) {
        String dedupKey = "stream-dedup:fx:" + FxType + FxCode;

        // setIfAbsent는 key가 없으면 true, 있으면 false 반환
        Boolean isFirst = redisTemplate.opsForValue().setIfAbsent(dedupKey, "1", DEDUP_TTL, java.util.concurrent.TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(isFirst)) {
            KisApiRequestDto dto = new KisApiRequestDto("fx", Map.of("fxType", FxType, "fxCode", FxCode));
            Map<String, Object> map = new ObjectMapper().convertValue(dto, new TypeReference<>() {});
            redisTemplate.opsForStream().add("kis-api-request", map);
        } else {
            log.info("fx가 이미 호출되었습니다." );
        }
    }

    /**
     * 인기 종목을 redis stream에 보내는 메서드
     */
    public void sendPopularRequest() {
        String dedupKey = "stream-dedup:popular:";

        // setIfAbsent는 key가 없으면 true, 있으면 false 반환
        Boolean isFirst = redisTemplate.opsForValue().setIfAbsent(dedupKey, "1", DEDUP_TTL, java.util.concurrent.TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(isFirst)) {
            KisApiRequestDto dto = new KisApiRequestDto("fx", Map.of("popular","true"));
            Map<String, Object> map = new ObjectMapper().convertValue(dto, new TypeReference<>() {});
            redisTemplate.opsForStream().add("kis-api-request", map);
        } else {
            log.info("popular가 이미 호출되었습니다." );
        }
    }

    /**
     * redis stream에 지수를 보내는 메서드, 가격정보를 시작일 부터 종료일까지 불러온다.
     * @param market 코스피, 코스닥
     * @param startDate 시작일
     * @param endDate 종료일
     */
    public void sendIndicesRequest(String market, String startDate, String endDate) {
        String dedupKey = "stream-dedup:indices:" + market;

        // setIfAbsent는 key가 없으면 true, 있으면 false 반환
        Boolean isFirst = redisTemplate.opsForValue().setIfAbsent(dedupKey, "1", DEDUP_TTL, java.util.concurrent.TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(isFirst)) {
            KisApiRequestDto dto = new KisApiRequestDto("indices", Map.of("market",market, "startDate", startDate, "endDate", endDate));
            Map<String, Object> map = new ObjectMapper().convertValue(dto, new TypeReference<>() {});
            redisTemplate.opsForStream().add("kis-api-request", map);
        } else {
            log.info("indices가 이미 호출되었습니다." );
        }
    }
}
