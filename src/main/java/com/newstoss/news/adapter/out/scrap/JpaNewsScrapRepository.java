package com.newstoss.news.adapter.out.scrap;

import com.newstoss.news.domain.NewsScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface JpaNewsScrapRepository extends JpaRepository<NewsScrap, UUID> {
    List<NewsScrap> findByMemberId(UUID memberId);
    boolean existsByMemberIdAndNewsId(UUID memberId, String newsId);
    NewsScrap findByMemberIdAndNewsId(UUID memberId, String newsId);
}
