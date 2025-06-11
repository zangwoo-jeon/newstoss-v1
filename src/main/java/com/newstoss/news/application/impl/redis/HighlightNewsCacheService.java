package com.newstoss.news.application.impl.news.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsMathRelatedDTO;
import com.newstoss.news.application.port.in.ml.v2.MatchNewsWithRelatedUseCase;
import com.newstoss.news.application.port.in.redis.HighlightNewsCacheUseCase;
import com.newstoss.news.application.port.out.ml.v2.MLNewsPortV2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.port.out.redis.HighlightNewsCachePort;
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

    @Override
    public void cacheHighlightWithRelatedNews() {
        List<NewsMathRelatedDTO> cached = cachePort.loadHighlightsWithRelated();
        if (cached != null && !cached.isEmpty()) {
            System.out.println("✅ 1차: Redis에서 캐시된 하이라이트 뉴스 반환");
            return;
        }

        boolean lockAcquired = cachePort.trySetInitCacheLock(Duration.ofSeconds(10));
        if (!lockAcquired) {
            System.out.println("⏳ 다른 인스턴스가 이미 캐시 작업 중 → 재조회 후 반환 시도");
            List<NewsMathRelatedDTO> fallback = cachePort.loadHighlightsWithRelated();
            if (fallback != null && !fallback.isEmpty()) {
                System.out.println("✅ 2차: 다른 인스턴스가 저장한 캐시 확인 후 반환");
                return;
            }

            System.out.println("❌ 캐시 재조회에도 없음 → 생략 or 예외 처리");
            return;
        }

        // 중복 제거: 내부 캐시 로직 재사용
        System.out.println("🚨 캐시 없음 → ML API 호출 및 Redis 저장");
        forceUpdateHighlightNewsCache();
    }

    @Override
    public void forceUpdateHighlightNewsCache() {
        List<MLNewsDTOv2> highlightsFromML = mlNewsPortV2.getHighLightNews().stream()
                .limit(5)
                .toList();

        List<NewsDTOv2> highlightDTOs = highlightsFromML.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();

        List<NewsMathRelatedDTO> result = matchUseCase.exec(highlightDTOs);

        cachePort.saveHighlightsWithRelated(result);
        System.out.println("✅ 강제 갱신: 하이라이트 뉴스 캐시 저장 완료");
    }
}