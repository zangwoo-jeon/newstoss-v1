package com.newstoss.news.adapter.in.web.sse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newstoss.news.adapter.in.web.news.dto.v2.ChatMessage;
import com.newstoss.news.adapter.in.web.sse.emitter.NewsSseEmitters;
import com.newstoss.news.application.redis.ChatStreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
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
    @Operation(summary = "챗봇 연결", description = "SSE 방식으로 챗봇 응답을 수신합니다.")
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestBody ChatMessage message) throws JsonProcessingException {
        System.out.println("✅ /chat/stream 컨트롤러 진입");
        UUID clientId = UUID.randomUUID();
        log.info("{}",clientId);
        System.out.println("💡 요청으로 생성된 UUID: " + clientId);
        return chatStreamService.handleStream(clientId, message);
    }
}