package com.newstoss.news.adapter.in.web.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.RedisAndSseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Slf4j
@Component
public class SseEmitters {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    public SseEmitter add() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            log.info("❌ 연결 종료됨 → 현재 연결 수: {}", emitters.size());
        });

        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            log.info("타임 아웃 으로 SSE 연결 종료 : {}", RedisAndSseErrorCode.SSE_CONNECTED_FAILURE.getMessage());
        });

        emitter.onError((e) -> {
            emitters.remove(emitter);
            log.info("SSE 연결 에러 : {} + {}", RedisAndSseErrorCode.SSE_CONNECTED_FAILURE.getMessage(),e.getMessage());

        });

        return emitter;
    }

    public void send(Object data) {
        if (emitters.isEmpty()) {
            log.info("연결된 클라이언트 없음 {} ", RedisAndSseErrorCode.SSE_NO_CONNECTED_CLIENT.getMessage());
            return;
        }

        System.out.println("📡 현재 등록된 emitter 수: " + emitters.size());

        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("news")
                        .data(data));
            } catch (IOException e) {
                log.info("전송 에러 : {} + {}", RedisAndSseErrorCode.SSE_SEND_FAILURE.getMessage(),e.getMessage());
                emitter.complete();
                emitters.remove(emitter);
            }
        });
    }
}
