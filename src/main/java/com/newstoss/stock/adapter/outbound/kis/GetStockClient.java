package com.newstoss.stock.adapter.outbound.kis;

import com.newstoss.global.errorcode.StockErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.global.kis.token.KisTokenManager;
import com.newstoss.global.kis.token.KisTokenProperties;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockNameDto;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisOutputDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.out.kis.StockInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetStockClient implements StockInfoPort {
    private final KisTokenProperties kisProperties;
    private final KisTokenManager kisTokenManager;
    private final RestTemplate restTemplate;

    public KisStockDto getStockInfo(String stockCode) {
        String token = kisTokenManager.getToken();
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kisProperties.getAppkey());
        headers.set("appsecret", kisProperties.getAppsecret());
        headers.set("tr_id", "FHKST01010100");
        headers.set("custtype", "P");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_INPUT_ISCD", stockCode);
        URI finalUri = uri.build().encode().toUri();

        try {
            ResponseEntity<KisOutputDto<KisStockDto>> response = restTemplate.exchange(
                    finalUri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
                );
            if (response.getBody() != null) {
                System.out.println("response = " + response);
                return response.getBody().getOutput();
            } else {
                log.error("KIS API 응답이 null입니다.");
                return null;
            }
        } catch (HttpServerErrorException e) {
            log.error(e.getMessage(),e);
            return null;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return null;
        }

    }

    @Override
    public List<KisPeriodStockDto> getStockInfoByPeriod(String stockCode, String period, String startDate, String endDate) {
        System.out.println("stockCode,period, startDate,endDate = " + stockCode + period + startDate +endDate);
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
                .queryParam("FID_INPUT_ISCD", stockCode)
                .queryParam("FID_INPUT_DATE_1", startDate )
                .queryParam("FID_INPUT_DATE_2", endDate)
                .queryParam("FID_PERIOD_DIV_CODE", period)
                .queryParam("FID_ORG_ADJ_PRC" , "1"); // 1: 수정주가, 0: 수정주가 미적용
        URI finalUri = uri.build().encode().toUri();

        try {
            ResponseEntity<KisApiResponseDto<KisStockDto,List<KisPeriodStockDto>>> response = restTemplate.exchange(
                    finalUri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );
            System.out.println("response.getBody() = " + response.getBody());
            return response.getBody().getOutput2();
        } catch (HttpServerErrorException e) {
            log.error(e.getMessage(),e);
            return null;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return null;
        } catch (NullPointerException e) {
            throw new CustomException(StockErrorCode.KIS_NULL_CODE);
        }
    }

    @Override
    public String getStockName(String stockCode) {
        String token = kisTokenManager.getToken();
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/search-info";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kisProperties.getAppkey());
        headers.set("appsecret", kisProperties.getAppsecret());
        headers.set("tr_id", "CTPF1604R");
        headers.set("custtype", "P");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("PDNO", stockCode)
                .queryParam("PRDT_TYPE_CD", "300");
        URI finalUri = uri.build().encode().toUri();

        ResponseEntity<KisOutputDto<KisStockNameDto>> response = restTemplate.exchange(
                finalUri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        System.out.println("Stock Name response = " + response);
        return response.getBody().getOutput().getStockNameAbrvName();

    }
}
