package com.newstoss.news.adapter.in.web.news.controller;

import com.newstoss.global.jwt.JwtProvider;
import com.newstoss.global.response.SuccessResponse;
import com.newstoss.member.domain.UserAccount;
import com.newstoss.news.adapter.in.web.news.dto.v2.*;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.NewsMetaDataDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedNewsDTOv2;
import com.newstoss.news.application.news.service.NewsServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "뉴스 API V2", description = "뉴스 관련 API V2")
@RequestMapping("/api/news/v2")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class NewsControllerV2{
    private final NewsServiceV2 newsServiceV2;
    private final JwtProvider jwtProvider;

//    @Operation(summary = "실시간 뉴스 조회", description = "최신 뉴스 10개를 조회합니다. redis pub/sub으로 종속 예정")
//    @GetMapping("/top10")
//    public ResponseEntity<SuccessResponse<Object>> top10news(){
//        List<NewsDTOv2> topNews = newsServiceV2.getRealTimeNews();
//        return ResponseEntity.ok(new SuccessResponse<>(true, "실시간 뉴스 10개 불러오기 성공", topNews));
//    }

    @Operation(summary = "뉴스 상세 조회", description = "특정 뉴스 ID에 해당하는 뉴스 상세 정보를 조회합니다.")
    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<Object>> newsdetail(
            @RequestParam String newsId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("🔥 Controller authentication: {}", authentication);
        log.info("🔥 Principal: {}", authentication.getPrincipal());
        UUID memberId = null;

        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof UserAccount userAccount) {
            memberId = userAccount.getMemberId(); // ✅ 인증된 사용자만 추출
            log.info("[newsdetail] 인증된 사용자 ID: {}", memberId);
        } else {
            log.info("[newsdetail] 비회원 요청");
        }

        NewsDTOv2 detailNews = newsServiceV2.getDetailNews(newsId, memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "뉴스 상세 조회 성공", detailNews));
    }
    @Operation(summary = "유사 뉴스 조회", description = "특정 뉴스와 유사한 과거 뉴스를 조회합니다.")
    @GetMapping("/related/news")
    public ResponseEntity<SuccessResponse<Object>> relatedNews(@RequestParam String newsId){
        List<RelatedNewsDTOv2> news = newsServiceV2.getRelatedNews(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "과거 유사 뉴스 조회 성공", news));
    }

    @Operation(summary = "맞춤 뉴스", description = "유저의 맞춤뉴스를 불러옵니다.")
    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<Object>> recommend(@RequestParam UUID userId){
        List<RecommendNewsDTO> news = newsServiceV2.recommedNews(userId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "과거 유사 뉴스 조회 성공", news));
    }

    @Operation(summary = "뉴스 검색", description = "ML api를 통해 뉴스 검색을 합니다.")
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<Object>> newSearch(@RequestParam String search){
        List<NewsDTOv2> searchNews = newsServiceV2.searchNews(search);
        return ResponseEntity.ok(new SuccessResponse<>(true,"뉴스 검색 성공", searchNews));
    }

    @Operation(summary = "전체 뉴스 조회", description = "입력 받은 파라미터 값에 따라 뉴스를 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<Object>> relatedReport(@ModelAttribute GetAllNewsDTO getAllNewsDTO){
        List<NewsDTOv2> news = newsServiceV2.getAllNews(getAllNewsDTO);
        return ResponseEntity.ok(new SuccessResponse<>(true, "전체 뉴스 조회 성공", news));
    }

//    @Operation(summary = "하이라이트 뉴스 조회", description = "하이라이트 뉴스를 조회합니다. redis캐시에 종속 예정")
//    @GetMapping("/highlight")
//    public ResponseEntity<SuccessResponse<Object>> highlight(){
//        List<HighlightNewsDTO> news = newsServiceV2.getHighlightNews();
//        return ResponseEntity.ok(new SuccessResponse<>(true, "하이라이트 뉴스 조회 성공", news));
//    }

    @Operation(summary = "하이라이트 뉴스 with redis 캐시", description = "하이라이트 뉴스를 조회합니다.")
    @GetMapping("/highlight/redis")
    public ResponseEntity<SuccessResponse<Object>> highlight2(){
        List<NewsMathRelatedDTO<HighlightNewsDTO>> news = newsServiceV2.highlightWithRedis();
        return ResponseEntity.ok(new SuccessResponse<>(true, "하이라이트 뉴스 조회 성공", news));
    }

    @Operation(summary = "뉴스 메타데이터를 조회", description = "뉴스 아이디, 요약본, 관련 종목, 관련종목 리스트를 보여줍니다")
    @GetMapping("/meta")
    public ResponseEntity<SuccessResponse<Object>> meta(@RequestParam String newsId){
        NewsMetaDataDTO news = newsServiceV2.getNewsMeta(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "메타데이터 조회 성공", news));
    }

    @Operation(summary = "종목과 관현된 뉴스 조회", description = "종목코드를 입력하면 관련된 뉴스를 조회합니다.")
    @GetMapping("/stocknews")
    public ResponseEntity<SuccessResponse<Object>> meta(@ModelAttribute StockNewsDTO stockNewsDTO){
        List<NewsDTOv2> news = newsServiceV2.stockNews(stockNewsDTO);
        return ResponseEntity.ok(new SuccessResponse<>(true, "메타데이터 조회 성공", news));
    }

    @Operation(summary = "뉴스 external 조회", description = "뉴스 external를 조회합니다.")
    @GetMapping("/external")
    public ResponseEntity<SuccessResponse<Object>> external(@RequestParam String newsId){
        ExternalDTO news = newsServiceV2.extenal(newsId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "외부변수 조회성공", news));
    }
}
