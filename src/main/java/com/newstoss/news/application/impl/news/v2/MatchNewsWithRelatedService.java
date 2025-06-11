package com.newstoss.news.application.impl.news.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsMathRelatedDTO;
import com.newstoss.news.adapter.in.web.dto.news.v2.RelatedNewsDTOv2;
import com.newstoss.news.application.port.in.ml.v2.MatchNewsWithRelatedUseCase;
import com.newstoss.news.application.port.out.ml.v2.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchNewsWithRelatedService implements MatchNewsWithRelatedUseCase {

    private final MLNewsPortV2 mlNewsPortV2;

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
}
