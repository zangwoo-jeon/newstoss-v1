package com.newstoss.news.application.query;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import com.newstoss.news.domain.MLNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRealTimeNews {
    private final MLNewsPort mlNewsPort;

    public List<NewsDTO> exec(){
        List<MLNewsDTO> news = mlNewsPort.getRealTimeNews();
        return news.stream().map(dto -> new NewsDTO(dto.getNewsId(), dto.getTitle(), dto.getUrl(), dto.getContent()))
                .toList();
    }
}
