package com.newstoss.news.TestCode.many;

import com.newstoss.news.TestCode.PerformanceTestHelper;
import com.newstoss.news.domain.many.MemberScrapTest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Slf4j
class ManyToManyPerformanceTest {

    @Autowired
    EntityManager em;

    @Test
    void testManyToManyFetchPerformance() {
        PerformanceTestHelper.prepareCommonDummyData(em);

        StopWatch sw = new StopWatch();
        sw.start();

        MemberScrapTest fetched = em.find(MemberScrapTest.class, PerformanceTestHelper.TEST_MEMBER_ID);
        int size = fetched.getNewsList().size();

        sw.stop();
        log.info("[ManyToMany] 연결된 뉴스 수: {}", size);
        log.info("[ManyToMany] 조회 시간: {}ms", sw.getTotalTimeMillis());
    }
}
