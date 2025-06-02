package com.newstoss.scrap.application.impl;

import com.newstoss.global.errorcode.ScrapErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.scrap.adapter.in.web.dto.ScrapDTO;
import com.newstoss.scrap.application.port.in.SaveScrapNewsUseCase;
import com.newstoss.scrap.application.port.out.ScrapNewsPort;
import com.newstoss.scrap.domain.NewsScrap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class SaveScrapNews implements SaveScrapNewsUseCase {
    private final ScrapNewsPort scrapNewsPort;

    public NewsScrap exec(ScrapDTO scrapDTO){
        if (scrapNewsPort.existsByMemberIdAndNewsId(scrapDTO.getMemberId(), scrapDTO.getNewsId())) {
            throw new CustomException(ScrapErrorCode.DUPLICATE_SCRAP);
        }

        NewsScrap newsScrap = NewsScrap.builder()
                .scrapNewsId(UUID.randomUUID())
                .memberId(scrapDTO.getMemberId())
                .newsId(scrapDTO.getNewsId())
                .build();
        return scrapNewsPort.save(newsScrap);
    }
}
