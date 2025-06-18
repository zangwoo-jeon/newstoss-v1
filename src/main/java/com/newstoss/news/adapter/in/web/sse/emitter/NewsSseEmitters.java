package com.newstoss.news.adapter.in.web.sse.emitter;

import com.newstoss.global.errorcode.RedisAndSseErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Component
public class NewsSseEmitters {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void initPingScheduler() {
        scheduler.scheduleAtFixedRate(this::sendPingToAll, 0, 1, TimeUnit.SECONDS); // 2초마다 ping
    }
    public SseEmitter add() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            log.info("❌ 연결 종료됨 → 현재 연결 수: {}", emitters.size());
        });

        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            log.info("⏱️ 타임아웃으로 SSE 연결 종료됨 → {}", RedisAndSseErrorCode.SSE_CONNECTED_FAILURE.getMessage());
        });

        emitter.onError((e) -> {
            emitters.remove(emitter);
            log.info("🚨 SSE 연결 에러 발생 → {}, 예외: {}", RedisAndSseErrorCode.SSE_CONNECTED_FAILURE.getMessage(), e.getMessage());
        });

        return emitter;
    }


    public void sendAll(Object data) {
        if (emitters.isEmpty()) {
            log.info("연결된 클라이언트 없음 {} ", RedisAndSseErrorCode.SSE_NO_CONNECTED_CLIENT.getMessage());
            return;
        }

        log.info("📡 전체 브로드캐스트 시작 – 등록된 emitter 수: {}", emitters.size());

        List<SseEmitter> toRemove = new CopyOnWriteArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("news")
                        .data(data));
            } catch (IOException | IllegalStateException e) {
                toRemove.add(emitter);
                log.debug("❗ Broken pipe 또는 SSE 전송 실패, 제거 예정 – {}", e.getMessage());
            }
        }
        // 따로 제거
        emitters.removeAll(toRemove);
    }
    private void sendPingToAll() {
        List<SseEmitter> toRemove = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("ping").data("💓"));
            } catch (IOException | IllegalStateException e) {
                emitter.complete();          // 명시적으로 연결 닫기
                toRemove.add(emitter);      // 반복 중 직접 remove하지 않기
            }
        }
        emitters.removeAll(toRemove);       // 반복 끝난 후 한꺼번에 제거
    }

    public int getEmitterCount() {
        return emitters.size();
    }
}
