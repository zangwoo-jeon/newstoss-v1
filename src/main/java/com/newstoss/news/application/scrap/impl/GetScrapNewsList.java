package com.newstoss.news.application.scrap.impl;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.scrap.dto.ExternalNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.scrap.port.in.GetScrapNewsListUseCase;
import com.newstoss.news.application.scrap.port.out.ScrapNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetScrapNewsList implements GetScrapNewsListUseCase {
    private final ScrapNewsPort scrapNewsPort;
    private final RestTemplate restTemplate;
    private static final String EXTERNAL_API_BASE_URL = "http://3.37.207.16:8000/news/v2/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public List<NewsDTOv2> exec(UUID memberId) {
        List<String> newsIds = scrapNewsPort.getAll(memberId);
        if (newsIds.isEmpty()) return List.of();
        
        return newsIds.stream()
                .map(this::getExternalNews)
                .filter(Objects::nonNull)
                .map(this::convertToNewsDTOv2)
                .toList();
    }

    private MLNewsDTOv2 getExternalNews(String newsId) {
        try {
            String url = EXTERNAL_API_BASE_URL + newsId;
            return restTemplate.getForObject(url, MLNewsDTOv2.class);
        } catch (Exception e) {
            // 외부 API 호출 실패 시 null 반환하여 필터링
            return null;
        }
    }

    private NewsDTOv2 convertToNewsDTOv2(MLNewsDTOv2 mlNewsDTOv2) {
        return NewsDTOv2Mapper.from(mlNewsDTOv2);
    }
}
