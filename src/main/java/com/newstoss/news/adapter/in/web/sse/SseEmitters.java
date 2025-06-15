package com.newstoss.news.adapter.in.web.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Component
public class SseEmitters {

    // memberId 기준 emitter 관리
    private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void initPingScheduler() {
        scheduler.scheduleAtFixedRate(this::sendPingToAll, 0, 30, TimeUnit.MINUTES); // 1시간마다 ping
    }

    public SseEmitter add(UUID memberId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(memberId, emitter);

        emitter.onCompletion(() -> {
            emitters.remove(memberId);
            log.info("❌ SSE 연결 종료됨 – memberId: {}, 현재 연결 수: {}", memberId, emitters.size());
        });

        emitter.onTimeout(() -> {
            emitters.remove(memberId);
            log.info("⏱️ SSE 타임아웃 – memberId: {}, 현재 연결 수: {}", memberId, emitters.size());
        });

        emitter.onError(e -> {
            emitters.remove(memberId);
            log.info("🚨 SSE 에러 – memberId: {}, 이유: {}", memberId, e.getMessage());
        });

        return emitter;
    }

    public void sendAll(Object data) {
        for (Map.Entry<UUID, SseEmitter> entry : emitters.entrySet()) {
            UUID memberId = entry.getKey();
            SseEmitter emitter = entry.getValue();

            try {
                System.out.println("🔥 send to memberId:"+ memberId);
                emitter.send(SseEmitter.event().name("news").data(data));
            } catch (IOException e) {
                emitters.remove(memberId);
                System.out.println("✅ emitter 제거 시도  제거 후 count:" + emitters.size());
                if (e.getMessage() != null && e.getMessage().contains("Broken pipe")) {
                    System.out.println("error:"+ memberId);
                    log.debug("❗ Broken pipe: 클라이언트가 연결 종료 – memberId: {}", memberId);
                } else {
                    System.out.println("error:"+ memberId);
                    log.warn("❗ SSE 전송 실패 – memberId: {}, 이유: {}", memberId, e.getMessage());
                }
            }
        }
    }

    private void sendPingToAll() {
        for (Map.Entry<UUID, SseEmitter> entry : emitters.entrySet()) {
            UUID memberId = entry.getKey();
            SseEmitter emitter = entry.getValue();

            try {
                emitter.send(SseEmitter.event().name("ping").data("💓")); // ping 전송
                log.debug("💓 ping 전송 – memberId: {}", memberId);
            } catch (IOException e) {
                emitters.remove(memberId);
                log.info("❌ ping 실패 – 연결 종료 – memberId: {}", memberId);
            }
        }
    }

    public int getEmitterCount() {
        return emitters.size();
    }
}
