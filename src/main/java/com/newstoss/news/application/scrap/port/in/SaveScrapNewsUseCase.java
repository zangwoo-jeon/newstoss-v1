package com.newstoss.news.application.scrap.port.in;

import com.newstoss.news.adapter.in.web.scrap.dto.ScrapDTO;
import com.newstoss.news.domain.NewsScrap;


public interface SaveScrapNewsUseCase {
    NewsScrap exec(ScrapDTO scrapDTO);
}
