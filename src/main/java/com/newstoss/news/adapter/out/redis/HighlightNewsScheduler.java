package com.newstoss.news.adapter.out.redis;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsMathRelatedDTO;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.impl.news.v2.NewsDTOv2Mapper;
import com.newstoss.news.application.port.in.ml.v2.MatchNewsWithRelatedUseCase;
import com.newstoss.news.application.port.in.redis.HighlightNewsCacheUseCase;
import com.newstoss.news.application.port.out.ml.v2.MLNewsPortV2;
import com.newstoss.news.application.port.out.redis.HighlightNewsCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HighlightNewsScheduler {

    private final HighlightNewsCacheUseCase highlightNewsCacheUseCase;
    private final MLNewsPortV2 mlNewsPortV2;
    private final MatchNewsWithRelatedUseCase matchUseCase;
    private final HighlightNewsCachePort cachePort;

    @Scheduled(cron = "0 0 0 1 * *") // 매일 00:00
    public void updateHighlightNews() {

        List<MLNewsDTOv2> highlightsFromML = mlNewsPortV2.getHighLightNews().stream()
                .limit(5)
                .toList();

        List<NewsDTOv2> highlightDTOs = highlightsFromML.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();

        List<NewsMathRelatedDTO> result = matchUseCase.exec(highlightDTOs);

        cachePort.saveHighlightsWithRelated(result);
        System.out.println("🗓️ 하이라이트 뉴스 캐시 갱신 완료");
    }
}
