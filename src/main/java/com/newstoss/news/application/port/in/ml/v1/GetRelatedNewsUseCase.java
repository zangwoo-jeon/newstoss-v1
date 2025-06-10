package com.newstoss.news.application.port.in.ml.v1;

import com.newstoss.news.adapter.in.web.dto.news.v1.RelatedNewsDTO;

import java.util.List;

public interface GetRelatedNewsUseCase {
    List<RelatedNewsDTO> exec(String newsId);
}
