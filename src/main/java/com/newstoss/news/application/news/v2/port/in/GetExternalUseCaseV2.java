package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.ExternalDTO;


public interface GetExternalUseCaseV2 {
    ExternalDTO exec(String newsId);
}
