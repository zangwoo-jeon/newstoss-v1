package com.newstoss.stock.application.sse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        sseEmitter.onCompletion(() -> emitters.remove(emitterId));
        sseEmitter.onTimeout(() -> emitters.remove(emitterId));
        sseEmitter.onError(error -> emitters.remove(emitterId));
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }


    @Override
    public Map<String, SseEmitter> findAllEmitterByUserId(UUID userId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId + "_"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllEmitterByUserId(UUID userId) {
        List<String> emitterIds = emitters.keySet().stream()
                .filter(key -> key.startsWith(userId + "_"))
                .toList();

        emitterIds.forEach(emitters::remove);
    }


    @Override
    public Map<String, SseEmitter> findAllEmitter() {
        return emitters;
    }
}
