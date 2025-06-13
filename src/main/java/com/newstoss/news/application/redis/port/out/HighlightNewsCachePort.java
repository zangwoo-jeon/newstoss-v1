package com.newstoss.news.application.redis.port.out;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.HighlightNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;

import java.time.Duration;
import java.util.List;

public interface HighlightNewsCachePort {
    void saveHighlightsWithRelated(List<NewsMathRelatedDTO<HighlightNewsDTO>> highlightData);
    List<NewsMathRelatedDTO<HighlightNewsDTO>> loadHighlightsWithRelated();
    boolean trySetInitCacheLock(Duration ttl);
}