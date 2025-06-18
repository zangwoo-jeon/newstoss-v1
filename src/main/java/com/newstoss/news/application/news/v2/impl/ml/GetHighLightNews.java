package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.HighlightNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLHighlightNewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetHighlightNewsUseCase;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetHighLightNews implements GetHighlightNewsUseCase {
    private final MLNewsPortV2 mlNewsPortV2;
    @Override
    public List<HighlightNewsDTO> exec() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusDays(7);

        List<MLHighlightNewsDTOv2> highlight = mlNewsPortV2.getHighLightNews(now, before);
        System.out.println("하이라이트 뉴스 "+highlight);
        return highlight.stream().map(NewsDTOv2Mapper::from).toList();
    }
}
