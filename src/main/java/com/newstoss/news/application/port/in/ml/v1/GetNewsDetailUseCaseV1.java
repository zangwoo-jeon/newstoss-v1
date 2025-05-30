package com.newstoss.news.application.port.in.ml.v1;

import com.newstoss.news.adapter.in.web.dto.v1.NewsDTOv1;

public interface GetNewsDetailUseCaseV1 {
    NewsDTOv1 exec(String newsId);
}
