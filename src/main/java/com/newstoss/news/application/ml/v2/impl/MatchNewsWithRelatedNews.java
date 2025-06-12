package com.newstoss.news.application.ml.v2.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.RelatedNewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedNewsDTOv1;
import com.newstoss.news.application.ml.v1.impl.NewsDTOv1Mapper;
import com.newstoss.news.application.ml.v1.port.in.GetRealTimeNewsUseCaseV1;
import com.newstoss.news.application.ml.v1.port.in.GetRelatedNewsUseCase;
import com.newstoss.news.application.ml.v2.port.in.GetRelatedNewsUseCaseV2;
import com.newstoss.news.application.ml.v2.port.in.MatchNewsWithRelatedUseCase;
import com.newstoss.news.application.ml.v1.port.out.MLNewsPortV1;
import com.newstoss.news.application.ml.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchNewsWithRelatedNews implements MatchNewsWithRelatedUseCase {

    private final MLNewsPortV1 mlNewsPortV1;
    private final MLNewsPortV2 mlNewsPortV2;
    private final GetRealTimeNewsUseCaseV1 getRealTimeNewsUseCase;
    private final GetRelatedNewsUseCase getRelatedNewsUseCase;

    @Override
    public List<NewsMathRelatedDTO> exec(List<NewsDTOv2> newsList) {
        return newsList.stream()
                .map(news -> {
                    List<RelatedNewsDTOv2> related = mlNewsPortV2.getSimilarNews(news.getNewsId())
                            .stream()
                            .limit(5)
                            .map(NewsDTOv2Mapper::from)
                            .toList();

                    return new NewsMathRelatedDTO(news, related);
                })
                .toList();
    }
    @Override
    public List<NewsMathRelatedDTOTest> test(List<NewsDTOv1> newsList) {
        return newsList.parallelStream()
                .map(news -> {
                    List<RelatedNewsDTO> related = getRelatedNewsUseCase.exec(news.getNewsId());
                    return new NewsMathRelatedDTOTest(news, related);
                })
                .toList();
    }
}