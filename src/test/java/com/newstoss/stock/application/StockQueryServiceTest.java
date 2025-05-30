package com.newstoss.stock.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.global.kis.KisTokenManager;
import com.newstoss.global.kis.KisTokenProperties;
import com.newstoss.stock.adapter.outbound.kis.dto.KisHTS20Stock;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@Transactional
class StockQueryServiceTest {

    @Autowired
    StockQueryService service;
    @Autowired
    KisTokenManager kisTokenManager;
    @Autowired
    KisTokenProperties kisTokenProperties;
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void category_find() {
        //given
        List<String> categories = service.getCategories();
        //when

        //then
        for (String category : categories) {
            System.out.println("category = " + category);
        }
    }

    @Test
    public void Category_kis() {
        String token = kisTokenManager.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        headers.set("appkey", kisTokenProperties.getAppkey());
        headers.set("appsecret", kisTokenProperties.getAppsecret());
        headers.set("tr_id", "FHPUP02140000");
        headers.set("custtype", "P");

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-index-category-price";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", "U")
                .queryParam("FID_INPUT_ISCD", "0001") // KOSPI
                .queryParam("FID_COND_SCR_DIV_CODE", "20214")
                .queryParam("FID_MRKT_CLS_CODE", "K") // KOSPI
                .queryParam("FID_BLNG_CLS_CODE", "0"); // 전체

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(), 
                GET,
                entity, 
                new ParameterizedTypeReference<>() {});
        System.out.println("Raw response: " + response.getBody());
//        List<examDto> output2 = response.getBody().getOutput2();
//        for (examDto Dto : output2) {
//            System.out.println("Dto.getName() = " + Dto.getName());
//        }

    }

    static class examDto {
        @JsonProperty("hts_kor_isnm")
        String Name;

        public String getName() {
            return Name;
        }

    }

    static class ResponseDto<T> {
        @JsonProperty("output2")
        List<T> Output2;
        public List<T> getOutput2() {
            return Output2;
        }
    }

    @Test
    public void HTS20() {
        //given
        String token = kisTokenManager.getToken();
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/ranking/hts-top-view";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kisTokenProperties.getAppkey());
        headers.set("appsecret", kisTokenProperties.getAppsecret());
        headers.set("tr_id", "FHPST02350000");
        headers.set("custtype", "P");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("");

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        System.out.println("Raw response: " + response.getBody());

        //when
        //then
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ResponseOutput1<T> {
        @JsonProperty("output1")
        private List<T> output2;
    }
}