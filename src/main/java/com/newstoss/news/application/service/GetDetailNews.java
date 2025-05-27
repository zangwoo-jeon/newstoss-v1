package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import com.newstoss.news.application.port.in.GetNewsDetailUseCase;
import com.newstoss.news.application.port.out.MLNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDetailNews implements GetNewsDetailUseCase {
    private final MLNewsPort mlNewsPort;

    @Override
    public NewsDTO exec(String newsId){
        MLNewsDTO news = mlNewsPort.getDetailNews(newsId);
        return new NewsDTO(news.getNewsId(), news.getTitle(), news.getUrl(), news.getContent());
    }
}
