package com.newstoss.news.adapter.in.web.sse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newstoss.news.adapter.in.web.sse.dto.ChatStreamRequest;
import com.newstoss.news.application.redis.ChatStreamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatSseController {

    private final ChatStreamService chatStreamService;

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestBody String request,
                             HttpServletRequest httpReq,
                             HttpServletResponse httpRes) throws JsonProcessingException {
        System.out.println("✅ /chat/stream 컨트롤러 진입");
        UUID clientId = getOrCreateClientId(httpReq, httpRes);
        log.info("{}",clientId);
        System.out.println("💡 요청으로 생성된 UUID: " + clientId);
        return chatStreamService.handleStream(clientId, request);
    }

    private UUID getOrCreateClientId(HttpServletRequest req, HttpServletResponse res) {
        // 쿠키 기반 UUID 발급 or 재사용
        return UUID.randomUUID();
    }
}