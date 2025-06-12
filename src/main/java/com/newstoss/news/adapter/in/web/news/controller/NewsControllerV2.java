package com.newstoss.news.adapter.in.web.news.controller;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.news.adapter.in.web.news.dto.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMetaDataDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.RelatedNewsDTOv2;
import com.newstoss.news.application.ml.service.NewsServiceV2;
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
        List<RelatedNewsDTOv2> news = newsServiceV2.getRelatedNews(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "과거 유사 뉴스 조회 성공", news));
    }



    @Operation(summary = "전체 뉴스 조회", description = "입력 받은 파라미터 값에 따라 뉴스를 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<Object>> relatedReport(@ModelAttribute GetAllNewsDTO getAllNewsDTO){
        List<NewsDTOv2> news = newsServiceV2.getAllNews(getAllNewsDTO);
        return ResponseEntity.ok(new SuccessResponse<>(true, "전체 뉴스 조회 성공", news));
    }

    @Operation(summary = "하이라이트 뉴스 조회", description = "하이라이트 뉴스를 조회합니다(ml api 미완성)")
    @GetMapping("/highlight")
    public ResponseEntity<SuccessResponse<Object>> highlight(){
        List<NewsDTOv2> news = newsServiceV2.getHighlightNews();
        return ResponseEntity.ok(new SuccessResponse<>(true, "하이라이트 뉴스 조회 성공", news));
    }

    @Operation(summary = "하이라이트 뉴스 with redis 캐시", description = "하이라이트 뉴스를 조회합니다(ml api 미완성)")
    @GetMapping("/highlight/reids")
    public ResponseEntity<SuccessResponse<Object>> highlight2(){
        List<NewsMathRelatedDTO> news = newsServiceV2.highlightWithRedis();
        return ResponseEntity.ok(new SuccessResponse<>(true, "하이라이트 뉴스 조회 성공", news));
    }

    @Operation(summary = "하이라이트 뉴스 with redis 캐시 test", description = "하이라이트 뉴스를 조회합니다(ml api 미완성)")
    @GetMapping("/highlight/reidstest")
    public ResponseEntity<SuccessResponse<Object>> highlighttest(){
        List<NewsMathRelatedDTOTest> news = newsServiceV2.highlightWithRedisTest();
        return ResponseEntity.ok(new SuccessResponse<>(true, "하이라이트 뉴스 조회 성공", news));
    }

    @Operation(summary = "뉴스 메타데이터를 조회", description = "뉴스 아이디, 요약본, 관련 종목, 관련종목 리스트를 보여줍니다")
    @GetMapping("/meta")
    public ResponseEntity<SuccessResponse<Object>> meta(@RequestParam String newsId){
        NewsMetaDataDTO news = newsServiceV2.getNewsMeta(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "메타데이터 조회 성공", news));
    }
}
