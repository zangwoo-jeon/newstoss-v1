package com.newstoss.news.adapter.out.scrap.repository;

import com.newstoss.news.adapter.out.scrap.dto.ScrapResponseDTO;
import com.newstoss.news.domain.NewsScrap;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface JpaNewsScrapRepository extends JpaRepository<NewsScrap, UUID> {

    List<NewsScrap> findByMember_MemberId(UUID memberId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM NewsScrap s WHERE s.member.memberId = :memberId AND s.newsId = :newsId")
    boolean existsByMember_MemberIdAndNewsId(@Param("memberId") UUID memberId, @Param("newsId") String newsId);

    NewsScrap findByMember_MemberIdAndNewsId(UUID memberId, String newsId);

    List<NewsScrap> findAll();

    @Query("SELECT s FROM NewsScrap s JOIN FETCH s.member")
    List<NewsScrap> findAllWithMember();

    @EntityGraph(attributePaths = "member")
    @Query("SELECT s FROM NewsScrap s")
    List<NewsScrap> findAllWithGraph();

    @Query("SELECT new com.newstoss.news.adapter.out.scrap.dto.ScrapResponseDTO(" +
            "s.scrapNewsId, m.memberId, m.name, s.newsId, '') " +
            "FROM NewsScrap s JOIN s.member m")
    List<ScrapResponseDTO> findScrapsAsDto();

    @Query("SELECT s FROM NewsScrap s WHERE s.member.memberId = :memberId")
    List<NewsScrap> findByMemberIdWithFetchJoin(@Param("memberId") UUID memberId);

    @EntityGraph(attributePaths = "member")
    @Query("SELECT s FROM NewsScrap s WHERE s.member.memberId = :memberId")
    List<NewsScrap> findByMemberIdWithGraph(@Param("memberId") UUID memberId);
}

