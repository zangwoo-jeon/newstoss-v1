package com.newstoss.news.application.ml.v1.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;

public interface GetNewsDetailUseCaseV1 {
    NewsDTOv1 exec(String newsId);
}
