package com.newstoss.news.application.impl.scrap;

import com.newstoss.news.adapter.in.web.dto.scrap.ScrapDTO;
import com.newstoss.news.application.port.in.scrap.DeleteScrapNewsUseCase;
import com.newstoss.news.application.port.out.scrap.ScrapNewsPort;
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
