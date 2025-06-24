package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedNewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLRelatedNewsDTOv2;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetRelatedNewsUseCaseV2;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetRelatedNewsV2 implements GetRelatedNewsUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public List<RelatedNewsDTOv2> exec(String newsId) {
        List<MLRelatedNewsDTOv2> rawSimilarNews = mlNewsPortV2.getSimilarNews(newsId);

        return rawSimilarNews.parallelStream()
                .map(NewsDTOv2Mapper::from)
                .toList();
    }
}
