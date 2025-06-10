package com.newstoss.savenews.application;

import com.newstoss.savenews.adapter.in.NewsDTO;
import com.newstoss.savenews.adapter.out.NewsClient;
import com.newstoss.savenews.adapter.out.NewsRepository;
import com.newstoss.news.domain.NewsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsClient newsClient;
    private final NewsRepository newsRepository;

    public void fetchAndStoreAllNews() {
        int skip = 23700;
        int limit = 100;

        while (true) {
            log.info("뉴스 수집 시작: skip={}, limit={}", skip, limit);

            List<NewsDTO> newsList = newsClient.getNews(skip, limit);

            // null newsId 로깅
            newsList.stream()
                    .filter(dto -> dto.getNewsId() == null)
                    .forEach(dto -> log.warn("newsId가 null인 뉴스 발견 => title: {}, date: {}", dto.getTitle(), dto.getWdate()));

            // 유효한 뉴스만 저장
            List<NewsEntity> entities = newsList.stream()
                    .filter(dto -> dto.getNewsId() != null)
                    .map(dto -> new NewsEntity(
                            dto.getNewsId(),
                            dto.getWdate(),
                            dto.getTitle(),
                            dto.getArticle(),
                            dto.getUrl(),
                            dto.getPress(),
                            dto.getImage()))
                    .toList();

            log.info("저장할 유효 뉴스 개수: {}", entities.size());

            if (entities.isEmpty()) {
                log.info("더 이상 저장할 뉴스가 없습니다. 종료합니다.");
                break;
            }

            newsRepository.saveAll(entities);
            log.info("뉴스 저장 완료: skip={} ~ {}", skip, skip + limit - 1);

            skip += limit;
        }

        log.info("전체 뉴스 수집 및 저장 완료");
    }
}
