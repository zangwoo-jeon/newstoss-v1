package com.newstoss.news.adapter.out.scrap;

import com.newstoss.news.adapter.in.web.dto.scrap.ScrapDTO;
import com.newstoss.news.adapter.out.dto.scrap.ScrapResponseDTO;
import com.newstoss.news.domain.NewsScrap;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface JpaNewsScrapRepository extends JpaRepository<NewsScrap, UUID> {

    List<NewsScrap> findByMember_MemberId(UUID memberId);

    boolean existsByMember_MemberIdAndNews_NewsId(UUID memberId, String newsId); // ✅ 수정

    NewsScrap findByMember_MemberIdAndNews_NewsId(UUID memberId, String newsId);

    List<NewsScrap> findAll();

    @Query("SELECT s FROM NewsScrap s JOIN FETCH s.member")
    List<NewsScrap> findAllWithMember();

    @EntityGraph(attributePaths = "member")
    @Query("SELECT s FROM NewsScrap s")
    List<NewsScrap> findAllWithGraph();

    @Query("SELECT new com.newstoss.news.adapter.out.dto.scrap.ScrapResponseDTO(" +
            "s.scrapNewsId, m.memberId, m.name, n.newsId, n.title) " +
            "FROM NewsScrap s JOIN s.member m JOIN s.news n")
    List<ScrapResponseDTO> findScrapsAsDto(); // ✅ 수정

    @Query("SELECT s FROM NewsScrap s JOIN FETCH s.news WHERE s.member.memberId = :memberId")
    List<NewsScrap> findByMemberIdWithFetchJoin(@Param("memberId") UUID memberId);

    @EntityGraph(attributePaths = "news")
    @Query("SELECT s FROM NewsScrap s WHERE s.member.memberId = :memberId")
    List<NewsScrap> findByMemberIdWithGraph(@Param("memberId") UUID memberId);
}

