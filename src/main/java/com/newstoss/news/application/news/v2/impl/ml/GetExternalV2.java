package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.ExternalDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLExternalDTO;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetAllNewsUseCaseV2;
import com.newstoss.news.application.news.v2.port.in.GetExternalUseCaseV2;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetExternalV2 implements GetExternalUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public ExternalDTO exec(String newsId) {
        MLExternalDTO news = mlNewsPortV2.external(newsId);
        return NewsDTOv2Mapper.extenal(news);
    }
}
