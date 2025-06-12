package com.newstoss.news.application.redis.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;
import com.newstoss.news.application.ml.v1.port.in.GetRealTimeNewsUseCaseV1;
import com.newstoss.news.application.ml.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.ml.v2.port.in.MatchNewsWithRelatedUseCase;
import com.newstoss.news.application.redis.port.in.HighlightNewsCacheUseCase;
import com.newstoss.news.application.ml.v2.port.out.MLNewsPortV2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.redis.port.out.HighlightNewsCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HighlightNewsCacheService implements HighlightNewsCacheUseCase {

    private final MLNewsPortV2 mlNewsPortV2;
    private final MatchNewsWithRelatedUseCase matchUseCase;
    private final HighlightNewsCachePort cachePort;
    private final GetRealTimeNewsUseCaseV1 getRealTimeNewsUseCaseV1;

    @Override
    public List<NewsMathRelatedDTO> cacheHighlightWithRelatedNews() {
        List<NewsMathRelatedDTO> cached = cachePort.loadHighlightsWithRelated();
        if (cached != null && !cached.isEmpty()) {
            System.out.println("✅ 1차: Redis에서 캐시된 하이라이트 뉴스 반환");
            return cached;
        }

        boolean lockAcquired = cachePort.trySetInitCacheLock(Duration.ofSeconds(10));
        if (!lockAcquired) {
            System.out.println("⏳ 다른 인스턴스가 이미 캐시 작업 중 → 재조회 후 반환 시도");
            List<NewsMathRelatedDTO> fallback = cachePort.loadHighlightsWithRelated();
            if (fallback != null && !fallback.isEmpty()) {
                System.out.println("✅ 2차: 다른 인스턴스가 저장한 캐시 확인 후 반환");
                return fallback;
            }

            System.out.println("❌ 캐시 재조회에도 없음 → 생략 or 예외 처리");
            return List.of();
        }

        // 중복 제거: 내부 캐시 로직 재사용
        System.out.println("🚨 캐시 없음 → ML API 호출 및 Redis 저장");
        List<NewsMathRelatedDTO> refreshed = forceUpdateHighlightNewsCache();
        System.out.println("✅ 강제 갱신: 하이라이트 뉴스 캐시 저장 완료");
        return refreshed;
    }

    @Override
    public List<NewsMathRelatedDTO> forceUpdateHighlightNewsCache() {
        List<MLNewsDTOv2> highlightsFromML = mlNewsPortV2.getHighLightNews().stream()
                .limit(5)
                .toList();

        List<NewsDTOv2> highlightDTOs = highlightsFromML.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();

        List<NewsMathRelatedDTO> result = matchUseCase.exec(highlightDTOs);

        cachePort.saveHighlightsWithRelated(result);
        return result;
    }

    @Override
    public List<NewsMathRelatedDTOTest> cacheHighlightWithRelatedNewsTest() {
        List<NewsMathRelatedDTOTest> cached = cachePort.loadHighlightsWithRelatedTest();
        if (cached != null && !cached.isEmpty()) {
            System.out.println("✅ 1차: Redis에서 캐시된 하이라이트 뉴스 반환");
            return cached;
        }

        boolean lockAcquired = cachePort.trySetInitCacheLock(Duration.ofSeconds(10));
        if (!lockAcquired) {
            System.out.println("⏳ 다른 인스턴스가 이미 캐시 작업 중 → 재조회 후 반환 시도");
            List<NewsMathRelatedDTOTest> fallback = cachePort.loadHighlightsWithRelatedTest();
            if (fallback != null && !fallback.isEmpty()) {
                System.out.println("✅ 2차: 다른 인스턴스가 저장한 캐시 확인 후 반환");
                return fallback;
            }

            System.out.println("❌ 캐시 재조회에도 없음 → 생략 or 예외 처리");
            return List.of(); // 혹은 예외 던지기
        }

        // 🚨 캐시가 아예 없어서 강제 갱신
        System.out.println("🚨 캐시 없음 → ML API 호출 및 Redis 저장");
        List<NewsMathRelatedDTOTest> refreshed = forceUpdateHighlightNewsCacheTest();
        System.out.println("✅ 강제 갱신: 하이라이트 뉴스 캐시 저장 완료");
        return refreshed;
    }

    @Override
    public List<NewsMathRelatedDTOTest> forceUpdateHighlightNewsCacheTest() {
        List<NewsDTOv1> highlightsFromML = getRealTimeNewsUseCaseV1.exec();
        List<NewsMathRelatedDTOTest> result = matchUseCase.test(highlightsFromML);
        cachePort.saveHighlightsWithRelatedTest(result);
        return result;
    }
}