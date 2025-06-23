package com.newstoss.news.adapter.in.web.sse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newstoss.news.adapter.in.web.news.dto.v2.ChatMessage;
import com.newstoss.news.adapter.in.web.sse.emitter.NewsSseEmitters;
import com.newstoss.news.application.redis.ChatStreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
@Slf4j
@Tag(name = "sse 연결", description = "sse 연결 api")
public class SseController {

    private final ChatStreamService chatStreamService;
    private final NewsSseEmitters newsSseEmitters;

    @Operation(summary = "실시간 뉴스 연결", description = "SSE 방식으로 실시간 뉴스 데이터를 수신합니다.")
    @GetMapping(value = "realtime", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        SseEmitter emitter = newsSseEmitters.add();
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
            log.info("✅ 연결 확인용 메시지 전송됨");
        } catch (IOException e) {
            System.out.println("❌ 연결 메시지 전송 실패: " + e.getMessage());
            log.error("❌ 연결 메시지 전송 실패: {}", e.getMessage());
            emitter.complete();
        }
        return emitter;
    }
//    @Operation(summary = "챗봇 연결", description = "챗봇을 연결합니다.")
//    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter stream(@RequestParam String message, HttpServletResponse response) throws JsonProcessingException {
//        log.info("✅ /chat/stream 컨트롤러 진입");
//        UUID clientId = UUID.randomUUID();
//        log.info("{}", clientId);
//        log.info("💡 요청으로 생성된 UUID:{} ", clientId);
//
//        // 🔹 여기서 SSE 관련 헤더 직접 지정
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("X-Accel-Buffering", "no"); // Nginx 안 쓰더라도 SSE 의도 명시
//        response.setHeader("Connection", "keep-alive");
//        response.setContentType("text/event-stream;charset=UTF-8");
//
//        try {
//            response.flushBuffer(); // 🔥 최초에 강제로 flush
//        } catch (IOException e) {
//            log.error("flush 실패", e);
//        }
//
//        return chatStreamService.handleStream(clientId, message);
//    }
    @Operation(summary = "챗봇 연결", description = "챗봇을 연결합니다")
    @GetMapping(value = "/stream/v2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void streamV2(@RequestParam String message, HttpServletResponse response) throws IOException {
        UUID clientId = UUID.randomUUID();
        log.info("📡 [v2] Writer 기반 SSE 연결 요청: {}", clientId);

        response.setContentType("text/event-stream;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("X-Accel-Buffering", "no");

        PrintWriter writer = response.getWriter();
        chatStreamService.registerWriter(clientId, writer); // writer 등록
        chatStreamService.sendToML(clientId, message);      // ML 호출 (Redis 발행)
    }

}