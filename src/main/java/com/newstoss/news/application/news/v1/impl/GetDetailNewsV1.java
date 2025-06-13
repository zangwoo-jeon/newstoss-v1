package com.newstoss.news.application.news.v1.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.news.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.news.v1.port.in.GetNewsDetailUseCaseV1;
import com.newstoss.news.application.news.v1.port.out.MLNewsPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDetailNewsV1 implements GetNewsDetailUseCaseV1 {
    private final MLNewsPortV1 mlNewsPortV1;

    @Override
    public NewsDTOv1 exec(String newsId){
        MLNewsDTOv1 news = mlNewsPortV1.getDetailNews(newsId);
        return NewsDTOv1Mapper.from(news);
    }
}
