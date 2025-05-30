package com.newstoss.news.application.service.v1;

import com.newstoss.news.adapter.in.web.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.port.in.v1.GetRealTimeNewsUseCaseV1;
import com.newstoss.news.application.port.out.MLNewsRelatedPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRealTimeNewsV1 implements GetRealTimeNewsUseCaseV1 {
    private final MLNewsRelatedPortV1 mlNewsRelatedPortV1;

    @Override
    public List<NewsDTOv1> exec(){
        List<MLNewsDTOv1> news = mlNewsRelatedPortV1.getRealTimeNews();
        return news.stream()
                .map(NewsDTOv1Mapper::from)
                .toList();
    }
}
