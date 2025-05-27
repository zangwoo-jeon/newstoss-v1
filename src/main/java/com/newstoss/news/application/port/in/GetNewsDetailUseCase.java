package com.newstoss.news.application.port.in;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;

public interface GetNewsDetailUseCase {
    NewsDTO exec(String newsId);
}
