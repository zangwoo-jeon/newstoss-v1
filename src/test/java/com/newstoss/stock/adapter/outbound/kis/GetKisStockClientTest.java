package com.newstoss.stock.adapter.outbound.kis;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.KisTokenManager;
import com.newstoss.global.kis.KisTokenProperties;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetKisStockClientTest {

    @Autowired
    private KisTokenProperties kisProperties;
    @Autowired
    private KisTokenManager kisTokenManager;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getStockInfoByPeriod() {
        String token = kisTokenManager.getToken();
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kisProperties.getAppkey());
        headers.set("appsecret", kisProperties.getAppsecret());
        headers.set("tr_id", "FHKST03010100");
        headers.set("custtype", "P");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_INPUT_ISCD", "005930")
                .queryParam("FID_INPUT_DATE_1", "20250101") // 시작일
                .queryParam("FID_INPUT_DATE_2", "20250531") // 종료일
                .queryParam("FID_PERIOD_DIV_CODE", "D")
                .queryParam("FID_ORG_ADJ_PRC" , "1"); // 1: 수정주가, 0: 수정주가 미적용


        ResponseEntity<KisApiResponseDto<KisStockDto,List<KisPeriodStockDto>>> response = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        System.out.println("response.getBody() = " + response.getBody().getOutput2());

    }
}