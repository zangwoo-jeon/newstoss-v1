package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.NewsMetaDataDTO;

public interface GetNewsMataDataUseCaseV2 {
    NewsMetaDataDTO exec(String newsId);
}
