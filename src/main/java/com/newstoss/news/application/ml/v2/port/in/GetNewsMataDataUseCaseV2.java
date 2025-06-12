package com.newstoss.news.application.ml.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMetaDataDTO;

public interface GetNewsMataDataUseCaseV2 {
    NewsMetaDataDTO exec(String newsId);
}
