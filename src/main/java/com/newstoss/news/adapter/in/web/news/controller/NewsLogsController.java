package com.newstoss.news.adapter.in.web.news.controller;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.news.adapter.in.web.news.dto.v2.LogEntryDTO;
import com.newstoss.news.application.news.service.NewsLogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "뉴스 로그 API", description = "뉴스 로그 API")
@RequestMapping("/api/newsLogs")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class NewsLogsController {
    private final NewsLogsService newsLogsService;

    @Operation(summary = "뉴스 로그 조회", description = "뉴스 로그 정보(memberId, newsId)를 조회합니다.")
    @GetMapping("")
    public ResponseEntity<SuccessResponse<Object>> getNewsLogs(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String memberId
            ){
        List<LogEntryDTO> logs = newsLogsService.getNewsLogs(startDate, endDate, memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true,"뉴스 로그 조회 성공", logs));
    }
}
