package com.newstoss.news.application.ml.v2.impl;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.ml.v2.port.in.GetHighlightNewsUseCase;
import com.newstoss.news.application.ml.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetHighLightNews implements GetHighlightNewsUseCase {
    private final MLNewsPortV2 mlNewsPortV2;
    @Override
    public List<NewsDTOv2> exec() {
        List<MLNewsDTOv2> highlight = mlNewsPortV2.getHighLightNews();
        return highlight.stream().map(NewsDTOv2Mapper::from).toList();
    }
}
