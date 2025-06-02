package com.newstoss.news.application.port.in.ml.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;

public interface GetNewsDetailUseCaseV2 {
    NewsDTOv2 exec(String newsId);
}
