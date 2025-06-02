package com.newstoss.scrap.application.impl;

import com.newstoss.scrap.adapter.in.web.dto.ScrapDTO;
import com.newstoss.scrap.application.port.in.DeleteScrapNewsUseCase;
import com.newstoss.scrap.application.port.out.ScrapNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteScapNews implements DeleteScrapNewsUseCase {
    private final ScrapNewsPort scrapNewsPort;

    public boolean exec(ScrapDTO scrapDTO){
        UUID scrapNewsId = scrapNewsPort.findNewsScrapId(scrapDTO.getMemberId(), scrapDTO.getNewsId());
        return scrapNewsPort.delete(scrapNewsId);
    }
}
