package com.newstoss.news.adapter.in.web;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.in.web.dto.RelatedNewsDTO;
import com.newstoss.news.application.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/news")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/top10")
    public ResponseEntity<SuccessResponse<Object>> top10news(){
        List<NewsDTO> topNews = newsService.getRealTimeNews();
        return ResponseEntity.ok(new SuccessResponse<>(true, "실시간 뉴스 10개 불러오기 성공", topNews));
    }

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<Object>> newsdetail(@RequestParam String newsId){
        NewsDTO detailNews = newsService.getDetailNews(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true,"뉴스 상세 조회 성공", detailNews));
    }

    @GetMapping("/related/news")
    public ResponseEntity<SuccessResponse<Object>> relatedNews(@RequestParam String newsId){
        List<RelatedNewsDTO> news =newsService.getRelatedNew(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "과거 유사 뉴스 조회 성공", news));
    }
}
