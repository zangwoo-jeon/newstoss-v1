package com.newstoss.favorite.adapter.out;

import com.newstoss.favorite.adapter.out.dto.ExternalStockApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalStockApiClient {
    
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://43.201.62.55:8080/api/v2/stocks/info";
    
    public ExternalStockApiResponse getStockInfo(String stockCode) {
        try {
            String url = BASE_URL + "/" + stockCode;
            log.info("외부 API 호출 URL: {}", url);
            
            // 먼저 String으로 응답을 받아서 확인
            String rawResponse = restTemplate.getForObject(url, String.class);
            log.info("외부 API 원본 응답: {}", rawResponse);
            
            if (rawResponse == null) {
                log.error("외부 API 응답이 null입니다. stockCode: {}", stockCode);
                throw new RuntimeException("주식 정보를 가져올 수 없습니다.");
            }
            
            // String 응답을 DTO로 변환
            ExternalStockApiResponse response = restTemplate.getForObject(url, ExternalStockApiResponse.class);
            
            if (response == null) {
                log.error("외부 API 응답 파싱 실패. stockCode: {}", stockCode);
                throw new RuntimeException("주식 정보를 가져올 수 없습니다.");
            }
            
            // Success 필드 확인 (true이면 성공)
            log.info("외부 API Success 필드 값: {}", response.isSuccess());
            if (!response.isSuccess()) {
                log.error("외부 API 호출 실패 - stockCode: {}, message: {}", stockCode, response.getMessage());
                throw new RuntimeException("주식 정보 조회 실패: " + response.getMessage());
            }
            
            // data 필드 확인
            if (response.getData() == null) {
                log.error("외부 API 응답에 data가 없습니다. stockCode: {}", stockCode);
                throw new RuntimeException("주식 데이터가 없습니다.");
            }
            
            log.info("외부 API 호출 성공 - stockCode: {}, stockName: {}", stockCode, response.getData().getStockName());
            return response;
            
        } catch (Exception e) {
            log.error("외부 API 호출 실패 - stockCode: {}, error: {}", stockCode, e.getMessage());
            log.error("전체 에러 스택: ", e);
            throw new RuntimeException("주식 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
} 