package com.newstoss.scrap.application.port.in;

import com.newstoss.scrap.adapter.in.web.dto.ScrapDTO;
import com.newstoss.scrap.domain.NewsScrap;


public interface SaveScrapNewsUseCase {
    NewsScrap exec(ScrapDTO scrapDTO);
}
