package com.newstoss.news.application.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newstoss.news.adapter.in.web.news.dto.v2.ChatMessage;
import com.newstoss.news.adapter.in.web.sse.emitter.ChatStreamEmitters;
import com.newstoss.news.adapter.out.redis.subscriber.ChatRedisSubscriber;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatStreamService {

    private final MLNewsPortV2 mlNewsPortV2;
    private final ChatStreamEmitters emitters;
    private final ChatRedisSubscriber subscriber;

//    public SseEmitter handleStream(UUID clientId, String message) throws JsonProcessingException {
//        SseEmitter emitter = new SseEmitter(300_000L);
//        emitter.onCompletion(() -> emitters.remove(clientId));
//        emitter.onTimeout(() -> emitters.remove(clientId));
//        emitter.onError((e) -> emitters.remove(clientId));
//        emitters.add(clientId, emitter);
//        String StringId = clientId.toString();
//        subscriber.dispatchForClientImmediately(clientId);
//        mlNewsPortV2.chat(StringId, message);
//        return emitter;
//    }

    public void registerWriter(UUID clientId, PrintWriter writer) {
        emitters.addWriter(clientId, writer);
    }

    public void sendToML(UUID clientId, String message) {
        mlNewsPortV2.chat(clientId.toString(), message);
    }
}