package com.newstoss.news.adapter.in.web.sse.emitter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatStreamEmitters {
    private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<UUID, PrintWriter> writers = new ConcurrentHashMap<>();

    public void add(UUID uuid, SseEmitter emitter) {
        emitters.put(uuid, emitter);
    }

    public Optional<SseEmitter> get(UUID uuid) {
        return Optional.ofNullable(emitters.get(uuid));
    }

    public void remove(UUID uuid) {
        emitters.remove(uuid);
    }
//-----------------------------------------------------------------------
    public void addWriter(UUID uuid, PrintWriter writer) {
        writers.put(uuid, writer);
    }

    public Optional<PrintWriter> getWriter(UUID uuid) {
        return Optional.ofNullable(writers.get(uuid));
    }

    public void removeWriter(UUID uuid) {
        writers.remove(uuid);
    }
}