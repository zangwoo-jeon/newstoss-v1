package com.newstoss.news.application.impl.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.sse.SseEmitters;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetRealTimeNewsWithSSE { //implements 해야함 sse 사용시

    private final SseEmitters sseEmitters;
    private final ObjectMapper objectMapper;

    public void publishNews(String message) {
        try {
            // Redis에서 수신한 JSON 문자열을 MLNewsDTO로 역직렬화
            MLNewsDTOv2 news = objectMapper.readValue(message, MLNewsDTOv2.class);

            // 클라이언트로 푸시
            sseEmitters.send(news);

            log.info("[SSE] 실시간 뉴스 전송 완료: {}", news.getTitle());
        } catch (Exception e) {
            log.error("[SSE] 뉴스 전송 실패", e);
        }
    }
}
