package com.newstoss.news.application.impl.news.v1;

import com.newstoss.news.adapter.in.web.dto.news.v1.RelatedNewsDTO;
import com.newstoss.news.adapter.out.dto.v1.MLRelatedNewsDTOv1;
import com.newstoss.news.application.port.in.ml.v1.GetRelatedNewsUseCase;
import com.newstoss.news.application.port.out.ml.v1.MLNewsPortV1;
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
