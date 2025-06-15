package com.newstoss.news.adapter.in.web.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.global.errorcode.RedisAndSseErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Component
public class SseEmitters {

    // memberId 기준 emitter 관리
    private final Map<UUID, SseEmitter> emittersWithID = new ConcurrentHashMap<>();
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void initPingScheduler() {
        scheduler.scheduleAtFixedRate(this::sendPingToAll, 0, 30, TimeUnit.SECONDS); // 1시간마다 ping
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

//    public SseEmitter addWithId(UUID memberId) {
//        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//        emittersWithID.put(memberId, emitter);
//
//        emitter.onCompletion(() -> {
//            emittersWithID.remove(memberId);
//            log.info("❌ SSE 연결 종료됨 – memberId: {}, 현재 연결 수: {}", memberId, emittersWithID.size());
//        });
//
//        emitter.onTimeout(() -> {
//            emittersWithID.remove(memberId);
//            log.info("⏱️ SSE 타임아웃 – memberId: {}, 현재 연결 수: {}", memberId, emittersWithID.size());
//        });
//
//        emitter.onError(e -> {
//            emittersWithID.remove(memberId);
//            log.info("🚨 SSE 에러 – memberId: {}, 이유: {}", memberId, e.getMessage());
//        });
//
//        return emitter;
//    }

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
            } catch (IOException e) {
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
            } catch (IOException e) {
                log.info("❌ ping 실패 – 연결 종료");
                emitter.complete();          // 💡 명시적으로 연결 닫기
                toRemove.add(emitter);      // 💡 반복 중 직접 remove하지 않기
            }
        }
        emitters.removeAll(toRemove);       // 💡 반복 끝난 후 한꺼번에 제거
    }

    public int getEmitterCount() {
        return emitters.size();
    }
}
