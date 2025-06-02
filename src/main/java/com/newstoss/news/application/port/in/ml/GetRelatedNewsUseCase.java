package com.newstoss.news.application.port.in.ml;

import com.newstoss.news.adapter.in.web.dto.news.common.RelatedNewsDTO;

import java.util.List;

public interface GetRelatedNewsUseCase {
    List<RelatedNewsDTO> exec(String newsId);
}
