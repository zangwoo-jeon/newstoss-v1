package com.newstoss.news.application.scrap.port.in;

import com.newstoss.news.adapter.in.web.scrap.dto.ScrapDTO;

public interface DeleteScrapNewsUseCase {
    boolean exec(ScrapDTO scrapDTO);
}
