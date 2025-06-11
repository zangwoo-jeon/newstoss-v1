package com.newstoss.news.application.port.in.redis;

public interface HighlightNewsCacheUseCase {
    void cacheHighlightWithRelatedNews();

    void forceUpdateHighlightNewsCache();
}
