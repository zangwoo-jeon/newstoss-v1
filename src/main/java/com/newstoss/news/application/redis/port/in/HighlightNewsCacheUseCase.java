package com.newstoss.news.application.redis.port.in;

public interface HighlightNewsCacheUseCase {
    void cacheHighlightWithRelatedNews();

    void forceUpdateHighlightNewsCache();

    void forceUpdateHighlightNewsCacheTest();
}
