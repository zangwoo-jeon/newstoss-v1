package com.newstoss.news.adapter.out.news.v1;

import com.newstoss.global.errorcode.NewsErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.news.adapter.out.dto.MLRelatedNewsDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedReportDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedReportListWrapper;
import com.newstoss.news.adapter.out.dto.MLRelatedStockDTO;
import com.newstoss.news.adapter.out.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.port.out.ml.MLNewsRelatedPortV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MLNewsRelatedAdapterV1 implements MLNewsRelatedPortV1 {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://3.37.207.16:8000/news/";

    @Override
    public List<MLNewsDTOv1> getRealTimeNews() {
        String url = BASE_URL + "?skip=0&limit=10";
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }

    @Override
    public MLNewsDTOv1 getDetailNews(String newsId) {
        String url = BASE_URL + newsId;
        return safeGetObject(url, MLNewsDTOv1.class);
    }

    @Override
    public List<MLRelatedNewsDTO> getSimilarNews(String newsId) {
        String url = BASE_URL + newsId + "/related/news";
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }
// stock_code, stock_name 줄 때 코드
//    @Override
//    public List<MLRelatedStockDTO> getRelatedStock(String newsId) {
//        String url = BASE_URL + newsId + "/related/stocks";
//        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
//    }

    // stock_name만 줄 때 코드
    @Override
    public List<MLRelatedStockDTO> getRelatedStock(String newsId) {
        String url = BASE_URL + newsId + "/related/stocks";
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }

    public List<MLRelatedReportDTO> getRelatedReport(String newsId) {
        String url = BASE_URL + newsId + "/related/reports";
        MLRelatedReportListWrapper wrapper = safeExchange(url, new ParameterizedTypeReference<>() {});
        return wrapper.getResults();
    }

    // 반환 값이 리스트고 응답 DTO랑 같을 경우
    private <T> List<T> safeExchangeList(String url, ParameterizedTypeReference<List<T>> typeRef) {
        try {
            ResponseEntity<List<T>> response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
            return Optional.ofNullable(response.getBody()).orElse(List.of());

        } catch (HttpClientErrorException.NotFound e) {
            throw new CustomException(NewsErrorCode.NEWS_NOT_FOUND);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new CustomException(NewsErrorCode.ML_INVALID_RESPONSE);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new CustomException(NewsErrorCode.ML_UNAUTHORIZED);
        } catch (org.springframework.web.client.ResourceAccessException e) {
            throw new CustomException(NewsErrorCode.ML_TIMEOUT);
        } catch (Exception e) {
            throw new CustomException(NewsErrorCode.ML_UNKNOWN_ERROR);
        }
    }
    // 반환 값이 리스트고 응답 DTO랑 같지 않을경우 ex) 응답이 result로 묶여있고 그 안에 리스트 형태로 응답이 올 경우
    private <T> T safeExchange(String url, ParameterizedTypeReference<T> typeRef) {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, typeRef).getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new CustomException(NewsErrorCode.NEWS_NOT_FOUND);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new CustomException(NewsErrorCode.ML_INVALID_RESPONSE);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new CustomException(NewsErrorCode.ML_UNAUTHORIZED);
        } catch (org.springframework.web.client.ResourceAccessException e) {
            throw new CustomException(NewsErrorCode.ML_TIMEOUT);
        } catch (Exception e) {
            log.error("[ML ERROR] 요청 실패. URL: {}, 예외: ", url, e);
            throw new CustomException(NewsErrorCode.ML_UNKNOWN_ERROR);
        }
    }
    // 응답이 리스트가 아닌 단일 객체로 올 경우
    private <T> T safeGetObject(String url, Class<T> responseType) {
        try {
            return restTemplate.getForObject(url, responseType);
        } catch (HttpClientErrorException.NotFound e) {
            throw new CustomException(NewsErrorCode.NEWS_NOT_FOUND);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new CustomException(NewsErrorCode.ML_INVALID_RESPONSE);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new CustomException(NewsErrorCode.ML_UNAUTHORIZED);
        } catch (org.springframework.web.client.ResourceAccessException e) {
            throw new CustomException(NewsErrorCode.ML_TIMEOUT);
        } catch (Exception e) {
            log.error("[ML ERROR] getRelatedStock 실패. URL: {}, 예외: ", url, e);
            throw new CustomException(NewsErrorCode.ML_UNKNOWN_ERROR);
        }
    }
}

