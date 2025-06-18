package com.newstoss.stock.application.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    Map<String, SseEmitter> findAllEmitterByUserId(UUID userId);

    void deleteById(String emitterId);

    void deleteAllEmitterByUserId(UUID userId);

    Map<String, SseEmitter> findAllEmitter();
}
