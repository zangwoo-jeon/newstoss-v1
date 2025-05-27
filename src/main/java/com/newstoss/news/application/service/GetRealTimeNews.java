package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import com.newstoss.news.application.port.in.GetRealTimeNewsUseCase;
import com.newstoss.news.application.port.out.MLNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRealTimeNews implements GetRealTimeNewsUseCase {
    private final MLNewsPort mlNewsPort;

    @Override
    public List<NewsDTO> exec(){
        List<MLNewsDTO> news = mlNewsPort.getRealTimeNews();
        return news.stream().map(dto -> new NewsDTO(dto.getNewsId(), dto.getTitle(), dto.getUrl(), dto.getContent()))
                .toList();
    }
}
