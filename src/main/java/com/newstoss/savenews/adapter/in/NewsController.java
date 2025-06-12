package com.newstoss.savenews.adapter.in;

import com.newstoss.savenews.application.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping("/import")
    public ResponseEntity<?> importNews() {
        try {
            newsService.fetchAndStoreAllNews();
            return ResponseEntity.ok("뉴스 수집 완료");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("뉴스 수집 중 오류 발생: " + e.getMessage());
        }
    }
}
