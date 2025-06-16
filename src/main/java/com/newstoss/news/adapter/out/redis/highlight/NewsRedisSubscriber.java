package com.newstoss.news.adapter.out.redis.highlight;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.news.dto.v2.RealTimeNewsDTO;
import com.newstoss.news.adapter.in.web.sse.NewsSseEmitters;
import com.newstoss.savenews.adapter.in.NewsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsRedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final NewsSseEmitters newsSseEmitters;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Redis 메시지 → 문자열
            String msg = new String(message.getBody());

            // JSON → DTO 역직렬화
            RealTimeNewsDTO dto = objectMapper.readValue(msg, RealTimeNewsDTO.class);

            if (dto.getNewsId() == null) {
                log.info("뉴스 ID 없음 → 제목: {}", dto.getTitle());
                return;
            }

            // 직렬화 테스트 로그
            String serialized = objectMapper.writeValueAsString(dto);
            log.info("SSE 전송 직렬화 데이터: {}", serialized);

            // SSE 전송
            newsSseEmitters.sendAll(dto);
            log.info("실시간 뉴스 저장 및 SSE 전송 완료 → {}", dto.getTitle());

        } catch (Exception e) {
            log.error("Redis 뉴스 파싱 또는 SSE 전송 오류", e);
        }
    }
}
