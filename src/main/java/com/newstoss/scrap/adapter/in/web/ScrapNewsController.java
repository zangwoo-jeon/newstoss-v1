package com.newstoss.scrap.adapter.in.web;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;
import com.newstoss.scrap.adapter.in.web.dto.ScrapDTO;
import com.newstoss.scrap.application.ScrapService;
import com.newstoss.scrap.domain.NewsScrap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/scrap")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ScrapNewsController {
    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Object>> scrap(@RequestBody ScrapDTO scrapDTO) {
        NewsScrap scrapNews = scrapService.scrap(scrapDTO);
        return ResponseEntity.ok(new SuccessResponse<>(true, "뉴스 스크랩 성공", null));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<Object>> getAllScrap(@RequestParam UUID memberId) {
        List<NewsDTOv2> scrapNewsList= scrapService.getAllScrap(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "스크랩 뉴스 전체 조회 성공", scrapNewsList));
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<Object>> delete(@RequestBody ScrapDTO scrapDTO) {
        boolean success = scrapService.delete(scrapDTO);
        return ResponseEntity.ok(new SuccessResponse<>(success, "스크랩 뉴스 삭제 성공", null));
    }
}
