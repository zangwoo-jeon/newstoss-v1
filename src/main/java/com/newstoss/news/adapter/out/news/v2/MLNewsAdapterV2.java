package com.newstoss.news.adapter.out.news.v2;

import com.newstoss.global.errorcode.NewsErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.news.adapter.in.web.news.dto.v2.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.sse.dto.ChatStreamRequest;
import com.newstoss.news.adapter.out.news.dto.v2.*;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class MLNewsAdapterV2 implements MLNewsPortV2 {

    private final RestTemplate restTemplate;

    public MLNewsAdapterV2(@Qualifier("mlRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private static final String BASE_URL = "http://3.37.207.16:8000/news/v2/";

//    @Override
//    public List<MLNewsDTOv2> getRealTimeNews() {
//        String url = BASE_URL + "?skip=0&limit=10";
//        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
//    }

    @Override
    public MLNewsDTOv2 getDetailNews(String newsId) {
        String url = BASE_URL + newsId;
        return safeGetObject(url, MLNewsDTOv2.class);
    }

    @Override
    public List<MLNewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO) {
        Integer skip = getAllNewsDTO.getSkip();
        Integer limit = getAllNewsDTO.getLimit();
        String url = BASE_URL + "?skip="+skip+"&limit="+limit;
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }


    @Override
    public List<MLRelatedNewsDTOv2> getSimilarNews(String newsId) {
        String url = BASE_URL + newsId + "/similar";
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<MLHighlightNewsDTOv2> getHighLightNews(LocalDateTime now, LocalDateTime before) {
        String url = BASE_URL + "highlights" + "?start_datetime=" + before + "&end_datetime=" + now;;
        System.out.println("ML 전송 url" + url);
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }

    @Override
    public MLNewsMataDataDTOv2 getNewsMeta(String newsId) {
        String url = BASE_URL + newsId + "/metadata" ;
        return safeGetObject(url, MLNewsMataDataDTOv2.class);
    }

    @Override
    public List<MLNewsDTOv2> searchNews(String searchNews){
        String url = BASE_URL + "/?skip=0&limit=10&title="+searchNews;
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }
    @Async("mlTaskExecutor")
    @Override
    public void chat(String clientId, String question) {
        ChatStreamRequest request = new ChatStreamRequest(clientId, question);
        String url = "http://15.165.211.100:8000/news/chat/stream";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ChatStreamRequest> entity = new HttpEntity<>(request, headers);

        try {
            restTemplate.postForEntity(url, entity, Void.class); // 응답을 기다리지 않음
        }     // 👉 커넥션 수 부족 / 풀 고갈 등
        catch (ResourceAccessException e) {
            if (e.getCause() instanceof java.net.ConnectException) {
                log.error("❌ [커넥션 실패] ML 서버에 연결할 수 없음 - clientId={}, {}", clientId, e.getMessage());
            } else if (e.getCause() instanceof java.net.SocketTimeoutException) {
                log.error("❌ [ML 응답 대기 중 타임아웃] clientId={} - {}", clientId, e.getMessage());
            } else {
                log.error("❌ [리소스 접근 예외] clientId={} - {}", clientId, e.getMessage());
            }
        }

        // 👉 4xx or 5xx 응답
        catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("❌ [ML 서버 응답 오류] 상태코드={} clientId={} - 응답본문={}", e.getStatusCode(), clientId, e.getResponseBodyAsString());
        }

        // 👉 그 외 예외
        catch (Exception e) {
            log.error("❌ [기타 예외] ML 요청 중 알 수 없는 오류 발생 - clientId={}", clientId, e);
        }
    }

    @Override
    public List<MLNewsDTOv2> stockToNews(int skip, int limit, String stock_code) {
        String url = BASE_URL + "?skip="+skip+"&limit="+limit+"&stock_list="+stock_code;

        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<MLRecommendNewsDTO> recommendNews(String memberId) {
        String url = BASE_URL + "recommend" + "?user_id="+memberId;
        return safeExchangeList(url, new ParameterizedTypeReference<>() {});
    }

    @Override
    public MLExternalDTO external(String newsId){
        String url = BASE_URL + newsId + "/external";
        return safeGetObject(url, MLExternalDTO.class);
    }
    // 반환 값이 리스트고 응답 DTO랑 같을 경우
    private <T> List<T> safeExchangeList(String url, ParameterizedTypeReference<List<T>> typeRef) {
        try {
            ResponseEntity<List<T>> response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
            return Optional.ofNullable(response.getBody()).orElse(List.of());

        } catch (HttpClientErrorException.NotFound e) {
            log.error("[ML ERROR] 요청 실패. URL: {}, 예외: ", url, e);
            throw new CustomException(NewsErrorCode.NEWS_NOT_FOUND);
        } catch (HttpClientErrorException.BadRequest e) {
            log.error("[ML ERROR] 요청 실패. URL: {}, 예외: ", url, e);
            throw new CustomException(NewsErrorCode.ML_INVALID_RESPONSE);
        } catch (HttpClientErrorException.Unauthorized e) {
            log.error("[ML ERROR] 요청 실패. URL: {}, 예외: ", url, e);
            throw new CustomException(NewsErrorCode.ML_UNAUTHORIZED);
        } catch (org.springframework.web.client.ResourceAccessException e) {
            log.error("[ML ERROR] 요청 실패. URL: {}, 예외: ", url, e);
            throw new CustomException(NewsErrorCode.ML_TIMEOUT);
        } catch (Exception e) {
            log.error("[ML ERROR] 요청 실패. URL: {}, 예외: ", url, e);
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

