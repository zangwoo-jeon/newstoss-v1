package com.newstoss.news.application.port.out.redis;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsMathRelatedDTO;

import java.time.Duration;
import java.util.List;

public interface HighlightNewsCachePort {
    void saveHighlightsWithRelated(List<NewsMathRelatedDTO> highlightData);
    List<NewsMathRelatedDTO> loadHighlightsWithRelated(); // ← 추가

    boolean trySetInitCacheLock(Duration ttl);
}