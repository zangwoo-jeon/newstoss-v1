package com.newstoss.news.application.port.in;

import com.newstoss.news.adapter.in.web.dto.RelatedNewsDTO;

import java.util.List;

public interface GetRelatedNewsUseCase {
    List<RelatedNewsDTO> exec(String newsId);
}
