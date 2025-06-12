package com.newstoss.news.application.ml.v1.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedNewsDTOv1;
import com.newstoss.news.application.ml.v1.port.in.GetRelatedNewsUseCase;
import com.newstoss.news.application.ml.v1.port.out.MLNewsPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetRelatedNewsV1 implements GetRelatedNewsUseCase {
    private final MLNewsPortV1 mlNewsPortV1;

    @Override
    public List<RelatedNewsDTO> exec(String newsId) {
        List<MLRelatedNewsDTOv1> rawSimilarNews = mlNewsPortV1.getSimilarNews(newsId);

        return rawSimilarNews.parallelStream().map(news -> new RelatedNewsDTO(
                news.getNewsId()
                ,news.getDate()
                ,news.getTitle()
                ,news.getContent()
                ,news.getUrl()
                ,news.getSimilarity()))
                .toList();
    }

}
