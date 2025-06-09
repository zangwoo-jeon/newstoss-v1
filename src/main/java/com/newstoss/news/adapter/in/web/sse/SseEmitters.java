package com.newstoss.news.adapter.in.web.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
            System.out.println("❌ Emitter 연결 종료됨 → 현재 수: " + emitters.size());
        });

        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            System.out.println("⏱️ Emitter 타임아웃 → 현재 수: " + emitters.size());
        });

        emitter.onError((e) -> {
            emitters.remove(emitter);
            System.out.println("💥 Emitter 에러 발생 → " + e.getMessage());
        });
        return emitter;
    }

    public void send(Object data) {
        System.out.println("현재 등록된 emitter 수: " + emitters.size());
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("news").data(data));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        });
    }
}
