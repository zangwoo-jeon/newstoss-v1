package com.newstoss.news.adapter.in.web.controller;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.news.adapter.in.web.dto.common.RelatedNewsDTO;
import com.newstoss.news.adapter.in.web.dto.common.RelatedReportDTO;
import com.newstoss.news.adapter.in.web.dto.common.RelatedStockDTO;
import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;
import com.newstoss.news.application.service.NewsServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "뉴스 API V2", description = "뉴스 관련 API V2")
@RequestMapping("/api/news/v2")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class NewsControllerV2{
    private final NewsServiceV2 newsServiceV2;

    @Operation(summary = "실시간 뉴스 조회", description = "최신 뉴스 10개를 조회합니다.")
    @GetMapping("/top10")
    public ResponseEntity<SuccessResponse<Object>> top10news(){
        List<NewsDTOv2> topNews = newsServiceV2.getRealTimeNews();
        return ResponseEntity.ok(new SuccessResponse<>(true, "실시간 뉴스 10개 불러오기 성공", topNews));
    }

    @Operation(summary = "뉴스 상세 조회", description = "특정 뉴스 ID에 해당하는 뉴스 상세 정보를 조회합니다.")
    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<Object>> newsdetail(@RequestParam String newsId){
        NewsDTOv2 detailNews = newsServiceV2.getDetailNews(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true,"뉴스 상세 조회 성공", detailNews));
    }

    @Operation(summary = "유사 뉴스 조회", description = "특정 뉴스와 유사한 과거 뉴스를 조회합니다.")
    @GetMapping("/related/news")
    public ResponseEntity<SuccessResponse<Object>> relatedNews(@RequestParam String newsId){
        List<RelatedNewsDTO> news = newsServiceV2.getRelatedNews(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "과거 유사 뉴스 조회 성공", news));
    }

    @Operation(summary = "뉴스 관련 종목 조회", description = "특정 뉴스와 연관된 주식 종목 리스트를 조회합니다.")
    @GetMapping("/related/stocks")
    public ResponseEntity<SuccessResponse<Object>> relatedStocks(@RequestParam String newsId){
        List<RelatedStockDTO> stocks = newsServiceV2.getRelatedStock(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "뉴스 관련 종목 조회 성공", stocks));
    }

    @Operation(summary = "뉴스 관련 리포트 조회", description = "특정 뉴스와 연관된 리포트 리스트를 조회합니다.(5개)")
    @GetMapping("/related/report")
    public ResponseEntity<SuccessResponse<Object>> relatedReport(@RequestParam String newsId){
        List<RelatedReportDTO> report = newsServiceV2.getRelatedReport(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "뉴스 관련 종목 조회 성공", report));
    }
}
