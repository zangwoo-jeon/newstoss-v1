package com.newstoss.stock.application;

import com.newstoss.global.kis.KisTokenManager;
import com.newstoss.global.kis.KisTokenProperties;
import com.newstoss.stock.adapter.outbound.kis.dto.KisApiResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndicePriceDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetIndiceInfoService {
    private final KisTokenManager kisTokenManager;
    private final KisTokenProperties kisTokenProperties;
    private final RestTemplate restTemplate;

    public List<KisIndicePriceDto> getIndiceInfo(String market, LocalDateTime startDate, LocalDateTime endDate) {
        String marketCode;
        if (market == "KOSPI") {
            marketCode = "0001";
        } else{
            marketCode = "1001";
        }
        String token = kisTokenManager.getToken();
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1//inquire-daily-indexchartprice";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kisTokenProperties.getAppkey());
        headers.set("appsecret", kisTokenProperties.getAppsecret());
        headers.set("tr_id", "FHKUP03500100");

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", "U")
                .queryParam("FID_INPUT_ISCD", marketCode)           // 코스피
                .queryParam("FID_INPUT_DATE_1", startDate.toString())
                .queryParam("FID_INPUT_DATE_2", endDate.toString())
                .queryParam("FID_PERIOD_DIV_CODE", "D");         // 일간
        try {
            ResponseEntity<KisApiResponseDto<KisIndicePriceDto>> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );
            return response.getBody().getOutput2();
        } catch (HttpServerErrorException e) {
            log.error(e.getMessage(),e);
            return null;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return null;
        }

    }


}
