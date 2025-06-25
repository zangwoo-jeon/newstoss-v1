package com.newstoss.news.application.redis.port.out;

import com.newstoss.news.adapter.in.web.news.dto.v2.HighlightNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.RealTimeNewsDTO;

import java.util.List;

public interface RealTimeNewsCachePort {
    void saveRealTimeNews(RealTimeNewsDTO recentNews);
    List<RealTimeNewsDTO> loadRecentNews();
}

