package com.newstoss.news.application.redis.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;

import java.util.List;

public interface HighlightNewsCacheUseCase {
    List<NewsMathRelatedDTO>cacheHighlightWithRelatedNews();

    List<NewsMathRelatedDTO> forceUpdateHighlightNewsCache();

    List<NewsMathRelatedDTOTest> cacheHighlightWithRelatedNewsTest();

    List<NewsMathRelatedDTOTest> forceUpdateHighlightNewsCacheTest();
}
