package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetNewsDetailUseCaseV2;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetDetailNewsV2 implements GetNewsDetailUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public NewsDTOv2 exec(String newsId, UUID memberId) {
        MLNewsDTOv2 news = mlNewsPortV2.getDetailNews(newsId);
        if (memberId == null) {
            log.info("[memberId : anonymous]  [newsId : {}]", newsId);
        }else{
            log.info("[memberId : {}] [newsId : {}]", memberId, newsId);
        }
        return NewsDTOv2Mapper.from(news);
    }
}
