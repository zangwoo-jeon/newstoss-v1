package com.newstoss.news.application.news.v1.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedNewsDTO;

import java.util.List;

public interface GetRelatedNewsUseCase {
    List<RelatedNewsDTO> exec(String newsId);
}
