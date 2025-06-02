package com.newstoss.scrap.application.port.out;

import com.newstoss.scrap.domain.NewsScrap;

import java.util.List;
import java.util.UUID;

public interface ScrapNewsPort {
    NewsScrap save(NewsScrap newsScrap);
    boolean delete(UUID scrapNewsId);
    List<String> getAll(UUID memberId);
    boolean existsByMemberIdAndNewsId(UUID memberId, String newsId);
    UUID findNewsScrapId(UUID memberId, String newsId);
}
