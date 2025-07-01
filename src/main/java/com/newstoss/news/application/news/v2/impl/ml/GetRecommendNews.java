package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.RecommendNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.RecommendNewsDateDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLRecommendNewsDTO;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetRecommendNewsUseCase;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetRecommendNews implements GetRecommendNewsUseCase {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public RecommendNewsDTO exec(UUID memberId) {
        String stmemberId = memberId.toString();
        MLRecommendNewsDTO news = mlNewsPortV2.recommendNews(stmemberId);
        return NewsDTOv2Mapper.mapToRecommend(news);
    }
}
