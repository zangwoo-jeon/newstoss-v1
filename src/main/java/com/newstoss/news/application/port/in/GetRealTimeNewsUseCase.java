package com.newstoss.news.application.port.in;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;

import java.util.List;

public interface GetRealTimeNewsUseCase {
    List<NewsDTO> exec();
}
