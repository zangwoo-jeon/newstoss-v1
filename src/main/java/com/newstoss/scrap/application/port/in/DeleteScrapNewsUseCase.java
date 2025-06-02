package com.newstoss.scrap.application.port.in;

import com.newstoss.scrap.adapter.in.web.dto.ScrapDTO;

import java.util.UUID;

public interface DeleteScrapNewsUseCase {
    boolean exec(ScrapDTO scrapDTO);
}
