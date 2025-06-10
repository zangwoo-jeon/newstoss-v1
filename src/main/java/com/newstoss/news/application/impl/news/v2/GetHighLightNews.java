package com.newstoss.news.application.impl.news.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.port.in.ml.v2.GetHighlightNewsUseCase;
import com.newstoss.news.application.port.out.ml.v2.MLNewsPortV2;
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
