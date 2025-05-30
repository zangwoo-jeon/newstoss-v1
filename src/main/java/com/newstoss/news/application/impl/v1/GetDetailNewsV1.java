package com.newstoss.news.application.impl.v1;

import com.newstoss.news.adapter.in.web.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.port.in.ml.v1.GetNewsDetailUseCaseV1;
import com.newstoss.news.application.port.out.MLNewsRelatedPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDetailNewsV1 implements GetNewsDetailUseCaseV1 {
    private final MLNewsRelatedPortV1 mlNewsRelatedPortV1;

    @Override
    public NewsDTOv1 exec(String newsId){
        MLNewsDTOv1 news = mlNewsRelatedPortV1.getDetailNews(newsId);
        return NewsDTOv1Mapper.from(news);
    }
}
