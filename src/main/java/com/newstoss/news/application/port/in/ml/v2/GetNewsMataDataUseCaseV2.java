package com.newstoss.news.application.port.in.ml.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsMetaDataDTO;

import java.util.List;

public interface GetNewsMataDataUseCaseV2 {
    NewsMetaDataDTO exec(String newsId);
}
