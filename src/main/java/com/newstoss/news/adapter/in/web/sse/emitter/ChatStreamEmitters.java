package com.newstoss.news.adapter.in.web.sse.emitter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatStreamEmitters {
    private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void add(UUID uuid, SseEmitter emitter) {
        emitters.put(uuid, emitter);
    }

    public Optional<SseEmitter> get(UUID uuid) {
        return Optional.ofNullable(emitters.get(uuid));
    }

    public void remove(UUID uuid) {
        emitters.remove(uuid);
    }
}