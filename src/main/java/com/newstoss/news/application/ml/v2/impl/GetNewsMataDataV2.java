package com.newstoss.news.application.ml.v2.impl;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMetaDataDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsMataDataDTOv2;
import com.newstoss.news.application.ml.v2.port.in.GetNewsMataDataUseCaseV2;
import com.newstoss.news.application.ml.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNewsMataDataV2 implements GetNewsMataDataUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public NewsMetaDataDTO exec(String newsId) {
        MLNewsMataDataDTOv2 mlMeta = mlNewsPortV2.getNewsMeta(newsId);
        System.out.println(mlMeta);
        return NewsDTOv2Mapper.from(mlMeta);
    }
}
