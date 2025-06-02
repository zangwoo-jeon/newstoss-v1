package com.newstoss.news.application.impl.scrap;

import com.newstoss.global.errorcode.ScrapErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.news.adapter.in.web.dto.scrap.ScrapDTO;
import com.newstoss.news.application.port.in.scrap.SaveScrapNewsUseCase;
import com.newstoss.news.application.port.out.scrap.ScrapNewsPort;
import com.newstoss.news.domain.NewsScrap;
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
