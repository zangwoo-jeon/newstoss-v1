package com.newstoss.stock.adapter.inbound.sse;

import com.newstoss.stock.application.sse.EmitterRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/stock/sse")
@Tag(name = "주식 조회 API V2" , description = "주식 조회 API V2")
@Slf4j
public class StockSseController {

    private final EmitterRepository emitterRepository;

    @GetMapping(value = "/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable("memberId")UUID memberId) throws IOException {
        log.info("주식 emitter 설정 완료");
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L);
        emitterRepository.save(emitterId, emitter);
        emitter.send(SseEmitter.event().name("connect").id(emitterId).data("connected!"));
        return emitter;
    }

    @GetMapping(value = "/guest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() throws IOException {
        log.info("주식 비회원 emitter 설정 완료");
        String emitterId = "guest" + "_" + System.currentTimeMillis();
        SseEmitter emitter = new SseEmitter(60 * 60 * 1000L);
        emitterRepository.save(emitterId, emitter);
        emitter.send(SseEmitter.event().name("connect").id(emitterId).data("비회원 connected!"));
        return emitter;
    }


}
