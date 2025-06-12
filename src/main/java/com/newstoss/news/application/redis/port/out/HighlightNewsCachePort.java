package com.newstoss.news.application.redis.port.out;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;

import java.time.Duration;
import java.util.List;

public interface HighlightNewsCachePort {
    void saveHighlightsWithRelated(List<NewsMathRelatedDTO> highlightData);
    void saveHighlightsWithRelatedTest(List<NewsMathRelatedDTOTest> highlightData);
    List<NewsMathRelatedDTO> loadHighlightsWithRelated();
    List<NewsMathRelatedDTOTest> loadHighlightsWithRelatedTest();// ← 추가
    boolean trySetInitCacheLock(Duration ttl);
}