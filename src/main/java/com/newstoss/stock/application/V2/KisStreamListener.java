//package com.newstoss.stock.application.V2;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.newstoss.global.errorcode.RedisStreamErrorCode;
//import com.newstoss.global.handler.CustomException;
//import com.newstoss.global.kis.dto.KisApiRequestDto;
//import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
//import com.newstoss.stock.application.port.out.kis.FxInfoPort;
//import com.newstoss.stock.application.port.out.kis.StockInfoPort;
//import com.newstoss.stock.application.port.out.persistence.LoadStockPort;
//import com.newstoss.stock.entity.Stock;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.stream.StreamListener;
//import org.springframework.data.redis.connection.stream.MapRecord;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class KisStreamListener implements StreamListener<String, MapRecord<String, Object, Object>> {
//
//    private final ObjectMapper objectMapper;
//    private final LoadStockPort loadStockPort;
//    private final StockInfoPort stockInfoPort;
//    private final FxInfoPort fxInfoPort;
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    @Override
//    @Transactional
//    public void onMessage(MapRecord<String, Object, Object> message) {
//        try {
//            KisApiRequestDto dto = objectMapper.convertValue(message.getValue(), KisApiRequestDto.class);
//
//            if ("stock".equals(dto.getType())) {
//                KisStockDto stockInfo = stockInfoPort.getStockInfo(dto.getStockCode());
//                log.info("주식 가격 변동 : {} 가격 : {}", dto.getStockCode(), stockInfo.getPrice());
//
//                Stock stock = loadStockPort.LoadStockByStockCode(dto.getStockCode());
//                stock.updateStockPrice(
//                        stockInfo.getPrice(),
//                        stockInfo.getChangeAmount(),
//                        stockInfo.getSign(),
//                        stockInfo.getChangeRate()
//                );
//            } else if ("fx".equals(dto.getType())) {
//                fxInfoPort.FxInfo(dto.getFxType(), dto.getFxCode());
//            }
//
//            redisTemplate.opsForStream().acknowledge("kis-api-request", "kis-group", message.getId());
//
//        } catch (Exception e) {
//            log.error("스트림 메시지 처리 실패: {}", e.getMessage(), e);
//            throw new CustomException(RedisStreamErrorCode.REDIS_CONSUMER_ERROR_CODE);
//        }
//    }
//}
//
