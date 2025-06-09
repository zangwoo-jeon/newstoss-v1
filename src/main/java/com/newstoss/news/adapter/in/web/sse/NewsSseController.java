package com.newstoss.news.adapter.in.web.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news/stream")
public class NewsSseController {
    private final SseEmitters sseEmitters;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        System.out.println("✅ [Controller] 클라이언트에서 SSE 연결 요청 들어옴");
        SseEmitter emitter = sseEmitters.add();
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
            System.out.println("✅ 연결 확인용 메시지 전송됨");
        } catch (IOException e) {
            System.out.println("❌ 연결 메시지 전송 실패: " + e.getMessage());
            emitter.complete();
        }
        return emitter;
    }
}
