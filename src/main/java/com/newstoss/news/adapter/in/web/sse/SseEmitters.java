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

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    public void send(Object data) {
        try {
            System.out.println("직렬화된 결과: " + objectMapper.writeValueAsString(data));
        } catch (Exception e) {
            System.out.println("직렬화 실패: " + e.getMessage());
        }

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
