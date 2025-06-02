package com.newstoss.news.application.port.in.scrap;

import com.newstoss.news.adapter.in.web.dto.scrap.ScrapDTO;

public interface DeleteScrapNewsUseCase {
    boolean exec(ScrapDTO scrapDTO);
}
