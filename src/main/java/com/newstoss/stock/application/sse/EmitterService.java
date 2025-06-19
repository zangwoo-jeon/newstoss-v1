package com.newstoss.stock.application.sse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmitterService {

    private final EmitterRepository emitterRepository;

    @Scheduled(fixedRate = 5000)
    public void sendPingToAllEmitters() {
        Map<String, SseEmitter> allEmitters = emitterRepository.findAllEmitter();
        allEmitters.forEach((emitterId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("ping").data("keep-alive"));
            } catch (Exception e) {
                log.warn("❌ ping 실패 → emitter 제거: {}", emitterId);
                emitterRepository.deleteById(emitterId);
            }
        });
    }


}
