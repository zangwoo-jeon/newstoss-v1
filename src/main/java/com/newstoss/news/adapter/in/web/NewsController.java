package com.newstoss.news.adapter.in.web;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.in.web.dto.RelatedNewsDTO;
import com.newstoss.news.adapter.in.web.dto.RelatedStockDTO;
import com.newstoss.news.application.NewsService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "실시간 뉴스 조회", description = "최신 뉴스 10개를 조회합니다.")
    @GetMapping("/top10")
    public ResponseEntity<SuccessResponse<Object>> top10news(){
        List<NewsDTO> topNews = newsService.getRealTimeNews();
        return ResponseEntity.ok(new SuccessResponse<>(true, "실시간 뉴스 10개 불러오기 성공", topNews));
    }

    @Operation(summary = "뉴스 상세 조회", description = "특정 뉴스 ID에 해당하는 뉴스 상세 정보를 조회합니다.")
    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<Object>> newsdetail(@RequestParam String newsId){
        NewsDTO detailNews = newsService.getDetailNews(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true,"뉴스 상세 조회 성공", detailNews));
    }

    @Operation(summary = "유사 뉴스 조회", description = "특정 뉴스와 유사한 과거 뉴스를 조회합니다.")
    @GetMapping("/related/news")
    public ResponseEntity<SuccessResponse<Object>> relatedNews(@RequestParam String newsId){
        List<RelatedNewsDTO> news = newsService.getRelatedNews(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "과거 유사 뉴스 조회 성공", news));
    }

    @Operation(summary = "뉴스 관련 종목 조회", description = "특정 뉴스와 연관된 주식 종목 리스트를 조회합니다.")
    @GetMapping("/related/stocks")
    public ResponseEntity<SuccessResponse<Object>> relatedStocks(@RequestParam String newsId){
        List<RelatedStockDTO> stocks = newsService.getRelatedStock(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "뉴스 관련 종목 조회 성공", stocks));
    }
}
