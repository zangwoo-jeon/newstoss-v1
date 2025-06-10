package com.newstoss.news.application.impl.news.v1;

import com.newstoss.news.adapter.in.web.dto.news.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.port.in.ml.v1.GetNewsDetailUseCaseV1;
import com.newstoss.news.application.port.out.ml.v1.MLNewsPortV1;
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
