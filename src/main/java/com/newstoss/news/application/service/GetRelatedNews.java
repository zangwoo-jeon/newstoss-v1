package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.RelatedNewsDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedNewsDTO;
import com.newstoss.news.application.port.in.GetRelatedNewsUseCase;
import com.newstoss.news.application.port.out.MLNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetRelatedNews implements GetRelatedNewsUseCase {
    private final MLNewsPort mlNewsPort;

    @Override
    public List<RelatedNewsDTO> exec(String newsId) {
        List<MLRelatedNewsDTO> rawSimilarNews = mlNewsPort.getSimilarNews(newsId);

        return rawSimilarNews.stream().map(news -> new RelatedNewsDTO(
                news.getNewsId()
                ,news.getDate()
                ,news.getTitle()
                ,news.getContent()
                ,news.getUrl()
                , news.getSimilarity()))
                .toList();
    }
}
