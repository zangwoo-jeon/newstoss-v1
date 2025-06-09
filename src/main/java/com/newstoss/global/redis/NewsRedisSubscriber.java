package com.newstoss.global.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.news.adapter.in.web.sse.SseEmitters;
import com.newstoss.news2.adapter.in.NewsDTO;
import com.newstoss.news2.adapter.out.NewsRepository;
import com.newstoss.news.domain.NewsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsRedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final NewsRepository newsRepository;
    private final SseEmitters sseEmitters;

    // 날짜 파싱용 포맷
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String msg = new String(message.getBody());
            NewsDTO dto = objectMapper.readValue(msg, NewsDTO.class);

            if (dto.getNewsId() == null) {
                log.info("뉴스 id 없음 : {}", dto.getTitle());
                return;
            }


            NewsEntity entity = new NewsEntity(
                    dto.getNewsId(), dto.getWdate(), dto.getTitle(), dto.getArticle(),
                    dto.getUrl(), dto.getPress(), dto.getImage()
            );

            newsRepository.save(entity); // 저장
            sseEmitters.send(dto);
            log.info("실시간 뉴스 저장 완료: {}", dto.getTitle());

        } catch (Exception e) {
            log.error("Redis 뉴스 파싱 오류", e);
        }
    }
}
