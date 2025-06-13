package com.newstoss.news.application.news.v1.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.news.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.news.v1.port.in.GetRealTimeNewsUseCaseV1;
import com.newstoss.news.application.news.v1.port.out.MLNewsPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRealTimeNewsV1 implements GetRealTimeNewsUseCaseV1 {
    private final MLNewsPortV1 mlNewsPortV1;

    @Override
    public List<NewsDTOv1> exec(){
        List<MLNewsDTOv1> news = mlNewsPortV1.getRealTimeNews();
        return news.stream()
                .map(NewsDTOv1Mapper::from)
                .toList();
    }
}
