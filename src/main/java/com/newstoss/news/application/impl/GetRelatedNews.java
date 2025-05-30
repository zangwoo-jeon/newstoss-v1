package com.newstoss.news.application.impl;

import com.newstoss.news.adapter.in.web.dto.common.RelatedNewsDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedNewsDTO;
import com.newstoss.news.application.port.in.ml.GetRelatedNewsUseCase;
import com.newstoss.news.application.port.out.MLNewsRelatedPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetRelatedNews implements GetRelatedNewsUseCase {
    private final MLNewsRelatedPortV1 mlNewsRelatedPortV1;

    @Override
    public List<RelatedNewsDTO> exec(String newsId) {
        List<MLRelatedNewsDTO> rawSimilarNews = mlNewsRelatedPortV1.getSimilarNews(newsId);

        return rawSimilarNews.stream().map(news -> new RelatedNewsDTO(
                news.getNewsId()
                ,news.getDate()
                ,news.getTitle()
                ,news.getContent()
                ,news.getUrl()
                ,news.getSimilarity()))
                .toList();
    }
}
