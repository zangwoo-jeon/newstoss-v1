package com.newstoss.stock.adapter.outbound.kis;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.token.KisTokenManager;
import com.newstoss.global.kis.token.KisTokenProperties;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisListOutputDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPopularDto;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.out.kis.PopularStockPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPopularStockService implements PopularStockPort {
    private final KisTokenProperties kisProperties;
    private final KisTokenManager kisTokenManager;
    private final RestTemplate restTemplate;
    private final StockRepository stockRepository;

    @Override
    public List<KisPopularDto> getPopularStock() {
        String token = kisTokenManager.getToken();
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/volume-rank";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kisProperties.getAppkey());
        headers.set("appsecret", kisProperties.getAppsecret());
        headers.set("tr_id", "FHPST01710000");
        headers.set("custtype", "P");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_COND_SCR_DIV_CODE", "20171")
                .queryParam("FID_INPUT_ISCD", "0000") // 0000: 전체
                .queryParam("FID_DIV_CLS_CODE", "0") // 0(전체) 1(보통주) 2(우선주)
                .queryParam("FID_BLNG_CLS_CODE", "3") // 0 : 평균거래량 1:거래증가율 2:평균거래회전율 3:거래금액순 4:평균거래금액회전율
                .queryParam("FID_TRGT_CLS_CODE", "111111111")
                .queryParam("FID_TRGT_EXLS_CLS_CODE", "0000000000")
                .queryParam("FID_INPUT_PRICE_1", "") // 가격 하한
                .queryParam("FID_INPUT_PRICE_2", "") // 가격 상한
                .queryParam("FID_VOL_CNT", "")
                .queryParam("FID_INPUT_DATE_1", ""); // 시작
        URI finalUri = builder.build().encode().toUri();
        try{
            ResponseEntity<KisListOutputDto<KisPopularDto>> response = restTemplate.exchange(
                    finalUri,
                    GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );
            
            List<KisPopularDto> popularStocks = response.getBody().getOutput();
            
            // 각 인기 종목에 대해 DB의 Stock 엔티티에서 이미지 정보를 가져와 설정
            popularStocks.forEach(stock -> {
                stockRepository.findByStockCode(stock.getMarketCode())
                        .ifPresent(s -> stock.setStockImage(s.getStockImage()));
            });
            
            return popularStocks;
        } catch (HttpServerErrorException e) {
            String body = e.getResponseBodyAsString();
            log.error("서버 에러 입니다: body: {} , e.message : {}", body, e.getMessage());
            throw new CustomException(StockErrorCode.KIS_NULL_CODE);
        }
    }
}
