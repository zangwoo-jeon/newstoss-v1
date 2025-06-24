package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;

import java.util.UUID;

public interface GetNewsDetailUseCaseV2 {
    NewsDTOv2 exec(String newsId, UUID member);
}
