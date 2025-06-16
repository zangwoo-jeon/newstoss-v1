//package com.newstoss.news.TestCode;
//
//
//import com.newstoss.member.domain.Member;
//import com.newstoss.news.domain.NewsScrap;
//import com.newstoss.news.domain.many.MemberScrapTest;
//import com.newstoss.news.domain.NewsEntity;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//public class PerformanceTestHelper {
//
//    public static final UUID TEST_MEMBER_ID = UUID.fromString("a8f9184d-0fa5-481f-9dc2-a7eaa15e1c2e");
//
//
//    public static void prepareCommonDummyData(EntityManager em) {
//        em.clear();
//
//        Query query = em.createQuery("SELECT n FROM NewsEntity n");
//        query.setMaxResults(50000);
//        System.out.println("MaxResults: " + query.getMaxResults());
//        List<NewsEntity> newsList = query.getResultList();
//        System.out.println("뉴스 개수: " + newsList.size());
//
//        Member member = em.find(Member.class, TEST_MEMBER_ID);
//        MemberScrapTest memberMany = em.find(MemberScrapTest.class, TEST_MEMBER_ID);
//        if (member == null || memberMany == null)
//            throw new IllegalStateException("공통 테스트 멤버가 존재하지 않습니다.");
//
////        List<NewsEntity> newsList = em.createQuery("SELECT n FROM NewsEntity n", NewsEntity.class)
////                .setMaxResults(50000)
////                .getResultList();
//        System.out.println("뉴스 개수: " + newsList.size());
//
//        List<NewsEntity> newsListMany = newsList;
//
//        // ManyToOne dummy 생성
//        Long count = em.createQuery("SELECT COUNT(s) FROM NewsScrap s WHERE s.member.memberId = :id", Long.class)
//                .setParameter("id", TEST_MEMBER_ID).getSingleResult();
//        if (count == 0) {
//            List<NewsScrap> scraps = new ArrayList<>();
//            for (int i = 0; i < 50000; i++) {
//                scraps.add(NewsScrap.builder()
//                        .scrapNewsId(UUID.randomUUID())
//                        .member(member)
//                        .news(newsList.get(i))
//                        .build());
//            }
//            scraps.forEach(em::persist);
//        }
//
//        // ManyToMany dummy 생성
//        if (memberMany.getNewsList().size() < 50000) {
//            memberMany.getNewsList().addAll(newsListMany);
//            em.persist(memberMany);
//        }
//
//        em.flush();
//        em.clear();
//    }
//}
