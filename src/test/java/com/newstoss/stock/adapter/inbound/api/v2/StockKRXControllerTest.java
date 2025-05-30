//package com.newstoss.stock.adapter.inbound.api.v2;
////
////import com.fasterxml.jackson.annotation.JsonProperty;
////import lombok.Data;
////import lombok.Value;
////import org.junit.jupiter.api.Test;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.core.ParameterizedTypeReference;
////import org.springframework.http.HttpEntity;
////import org.springframework.http.HttpHeaders;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.client.RestTemplate;
////import org.springframework.web.util.UriComponentsBuilder;
////
////import java.util.List;
////
////import static org.junit.jupiter.api.Assertions.*;
////
////@SpringBootTest
////class StockKRXControllerTest {
////
////    @Test
////    public void KRX() {
////        //given
////        String url = "http://data-dbg.krx.co.kr/svc/apis/sto/stk_bydd_trd";
////        String auth_key ="2DEB6FE994A74B0BA41B5E61156012DF88D27645";
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("auth_key", auth_key);
////        headers.set("Content-Type", "application/json");
////
////        HttpEntity<String> entity = new HttpEntity<>(headers);
////
////        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
////        builder.queryParam("basDd", "20250520");
////
////        ResponseEntity<ListResponse> response = new RestTemplate().exchange(
////                builder.toUriString(),
////                org.springframework.http.HttpMethod.GET,
////                entity,
////                new ParameterizedTypeReference<>() {}
////        );
////        List<KRXResponseDto> dtos = response.getBody().getOutBlock1();
////        //when
////
////        //then
////        for (KRXResponseDto dto : dtos) {
////            System.out.println("ISU_CD: " + dto.getIsuCode());
////            System.out.println("ISU_NM: " + dto.getIsuName());
////            System.out.println("TDD_CLSPRC: " + dto.getTddClosePrice());
////            System.out.println("----------------------------");
////        }
////    }
////
////    @Data
////    static class ListResponse {
////        @JsonProperty("OutBlock_1")
////        private List<KRXResponseDto> outBlock1;
////    }
////    @Data
////    static class KRXResponseDto {
////        @JsonProperty("ISU_CD")
////        private String isuCode;
////        @JsonProperty("ISU_NM")
////        private String isuName;
////        @JsonProperty("TDD_CLSPRC")
////        private String tddClosePrice;
////    }
////
////
////}