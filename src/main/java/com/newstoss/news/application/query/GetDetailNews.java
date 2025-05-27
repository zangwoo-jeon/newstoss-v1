package com.newstoss.news.application.query;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import com.newstoss.news.domain.MLNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDetailNews {
    private final MLNewsPort mlNewsPort;

    public NewsDTO exec(String newsId){
        MLNewsDTO news = mlNewsPort.getDetailNews(newsId);
        return new NewsDTO(news.getNewsId(), news.getTitle(), news.getUrl(), news.getContent());
    }
}
