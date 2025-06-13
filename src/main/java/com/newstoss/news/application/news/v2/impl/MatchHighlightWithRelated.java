package com.newstoss.news.application.news.v2.impl;

import com.newstoss.news.adapter.in.web.news.dto.v2.HighlightNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.RelatedNewsDTOv2;
import com.newstoss.news.application.news.v2.port.in.GetRelatedNewsUseCaseV2;
import com.newstoss.news.application.news.v2.port.in.MatchNewsWithRelatedUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchHighlightWithRelated implements MatchNewsWithRelatedUseCase<HighlightNewsDTO> {

    private final GetRelatedNewsUseCaseV2 getRelatedNewsUseCaseV2;

    @Override
    public List<NewsMathRelatedDTO<HighlightNewsDTO>> exec(List<HighlightNewsDTO> newsList) {
        return newsList.parallelStream()
                .map(news -> {
                    List<RelatedNewsDTOv2> related = getRelatedNewsUseCaseV2.exec(news.getNewsId());
                    return new NewsMathRelatedDTO<>(news, related);
                })
                .toList();
    }
    //맞춤 뉴스 api만들어지면 추가 예정
}
