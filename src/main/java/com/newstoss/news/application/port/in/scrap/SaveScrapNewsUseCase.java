package com.newstoss.news.application.port.in.scrap;

import com.newstoss.news.adapter.in.web.dto.scrap.ScrapDTO;
import com.newstoss.news.domain.NewsScrap;


public interface SaveScrapNewsUseCase {
    NewsScrap exec(ScrapDTO scrapDTO);
}
