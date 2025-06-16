//package com.newstoss.news.TestCode.one;
//
//import com.newstoss.news.TestCode.PerformanceTestHelper;
//import com.newstoss.news.domain.NewsScrap;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.util.StopWatch;
//
//import java.util.List;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//@Slf4j
//class ManyToOnePerformanceTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    void testManyToOneFetchPerformance() {
//        PerformanceTestHelper.prepareCommonDummyData(em);
//
//        StopWatch sw = new StopWatch();
//        sw.start();
//
//        List<NewsScrap> scraps = em.createQuery(
//                        "SELECT s FROM NewsScrap s JOIN FETCH s.member JOIN FETCH s.news WHERE s.member.memberId = :id",
//                        NewsScrap.class)
//                .setParameter("id", PerformanceTestHelper.TEST_MEMBER_ID)
//                .getResultList();
//
//        int size = scraps.size();
//
//        sw.stop();
//        log.info("[ManyToOne] 연결된 뉴스 수: {}", size);
//        log.info("[ManyToOne] 조회 시간: {}ms", sw.getTotalTimeMillis());
//    }
//}
