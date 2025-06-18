package com.newstoss.news.application.redis.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.HighlightNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;

import java.util.List;

public interface HighlightNewsCacheUseCase {
    List<NewsMathRelatedDTO<HighlightNewsDTO>>loadRedis();
    List<NewsMathRelatedDTO<HighlightNewsDTO>> UpdateRedis();
}
