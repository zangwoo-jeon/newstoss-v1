package com.newstoss.news.application.scrap.impl;

import com.newstoss.news.adapter.in.web.scrap.dto.ScrapDTO;
import com.newstoss.news.application.scrap.port.in.DeleteScrapNewsUseCase;
import com.newstoss.news.application.scrap.port.out.ScrapNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteScrapNews implements DeleteScrapNewsUseCase {
    private final ScrapNewsPort scrapNewsPort;

    public boolean exec(ScrapDTO scrapDTO){
        UUID scrapNewsId = scrapNewsPort.findNewsScrapId(scrapDTO.getMemberId(), scrapDTO.getNewsId());
        return scrapNewsPort.delete(scrapNewsId);
    }
}
