package com.newstoss.news.application.impl.ml.v2;

import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.port.in.ml.v2.GetNewsDetailUseCaseV2;
import com.newstoss.news.application.port.out.ml.MLNewsRelatedPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDetailNewsV2 implements GetNewsDetailUseCaseV2 {
    private final MLNewsRelatedPortV2 mlNewsRelatedPortV2;

    @Override
    public NewsDTOv2 exec(String newsId){
        MLNewsDTOv2 news = mlNewsRelatedPortV2.getDetailNews(newsId);
        return NewsDTOv2Mapper.from(news);
    }
}
