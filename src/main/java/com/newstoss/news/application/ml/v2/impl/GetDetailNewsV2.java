package com.newstoss.news.application.ml.v2.impl;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.ml.v2.port.in.GetNewsDetailUseCaseV2;
import com.newstoss.news.application.ml.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDetailNewsV2 implements GetNewsDetailUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public NewsDTOv2 exec(String newsId){
        MLNewsDTOv2 news = mlNewsPortV2.getDetailNews(newsId);
        return NewsDTOv2Mapper.from(news);
    }
}
