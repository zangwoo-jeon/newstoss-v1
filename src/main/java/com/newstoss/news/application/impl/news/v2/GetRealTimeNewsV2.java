package com.newstoss.news.application.impl.news.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.port.in.ml.v2.GetRealTimeNewsUseCaseV2;
import com.newstoss.news.application.port.out.ml.MLNewsRelatedPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRealTimeNewsV2 implements GetRealTimeNewsUseCaseV2 {
    private final MLNewsRelatedPortV2 mlNewsRelatedPortV2;

    @Override
    public List<NewsDTOv2> exec(){
        List<MLNewsDTOv2> news = mlNewsRelatedPortV2.getRealTimeNews();
        return news.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();
    }
}
