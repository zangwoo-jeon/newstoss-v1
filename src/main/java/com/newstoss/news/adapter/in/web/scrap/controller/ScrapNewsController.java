package com.newstoss.news.adapter.in.web.scrap.controller;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.scrap.dto.ScrapDTO;
import com.newstoss.news.application.scrap.service.ScrapService;
import com.newstoss.news.domain.NewsScrap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(name = "뉴스 스크랩 api", description = "뉴스 스크랩 (추가, 삭제, 조회)")
@RequestMapping("/api/scrap")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ScrapNewsController {
    private final ScrapService scrapService;

    @Operation(summary = "스크랩 추가", description = "뉴스 스크랩을 추가합니다.")
    @PostMapping
    public ResponseEntity<SuccessResponse<Object>> scrap(@RequestBody ScrapDTO scrapDTO) {
        NewsScrap scrapNews = scrapService.scrap(scrapDTO);
        return ResponseEntity.ok(new SuccessResponse<>(true, "뉴스 스크랩 성공", null));
    }
    @Operation(summary = "스크랩 조회", description = "모든 스크랩을 조회합니다.")
    @GetMapping
    public ResponseEntity<SuccessResponse<Object>> getAllScrap(@RequestParam UUID memberId) {
        List<NewsDTOv2> scrapNewsList= scrapService.getAllScrap(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "스크랩 뉴스 전체 조회 성공", scrapNewsList));
    }
    @Operation(summary = "스크랩 삭제", description = "뉴스 스크랩을 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<SuccessResponse<Object>> delete(@RequestBody ScrapDTO scrapDTO) {
        boolean success = scrapService.delete(scrapDTO);
        return ResponseEntity.ok(new SuccessResponse<>(success, "스크랩 뉴스 삭제 성공", null));
    }
}
