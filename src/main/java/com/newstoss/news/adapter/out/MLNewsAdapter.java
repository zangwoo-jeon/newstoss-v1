package com.newstoss.news.adapter.out;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.NewsErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import com.newstoss.news.domain.MLNewsPort;
import lombok.RequiredArgsConstructor;
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
public class MLNewsAdapter implements MLNewsPort {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "http://3.37.207.16:8000/news/";

    @Override
    public List<MLNewsDTO> getRealTimeNews() {
        try {
            ResponseEntity<List<MLNewsDTO>> response = restTemplate.exchange(
                    BASE_URL + "?skip=0&limit=10",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return Optional.ofNullable(response.getBody()).orElse(List.of());
        } catch (Exception e) {
            throw new CustomException(NewsErrorCode.ML_SERVER_ERROR);
        }
    }

    @Override
    public MLNewsDTO getDetailNews(String newsId) {
        try {
            return restTemplate.getForObject(BASE_URL + newsId, MLNewsDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new CustomException(NewsErrorCode.NEWS_NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(NewsErrorCode.ML_SERVER_ERROR);
        }
    }
}
