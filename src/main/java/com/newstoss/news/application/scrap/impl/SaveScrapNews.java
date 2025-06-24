package com.newstoss.news.application.scrap.impl;

import com.newstoss.global.errorcode.ScrapErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.domain.Member;
import com.newstoss.news.adapter.in.web.scrap.dto.ScrapDTO;
import com.newstoss.news.application.scrap.port.in.SaveScrapNewsUseCase;
import com.newstoss.news.application.scrap.port.out.ScrapNewsPort;
import com.newstoss.news.domain.NewsScrap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveScrapNews implements SaveScrapNewsUseCase {

    private final ScrapNewsPort scrapNewsPort;
    private final JpaMemberRepository memberRepository;

    @Override
    public NewsScrap exec(ScrapDTO scrapDTO) {
        UUID memberId = scrapDTO.getMemberId();
        String newsId = scrapDTO.getNewsId();

        if (scrapNewsPort.existsByMemberIdAndNewsId(memberId, newsId)) {
            throw new CustomException(ScrapErrorCode.DUPLICATE_SCRAP);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ScrapErrorCode.MEMBER_NOT_FOUND));

        NewsScrap newsScrap = NewsScrap.builder()
                .member(member)
                .newsId(newsId)
                .build();

        return scrapNewsPort.save(newsScrap);
    }
}
