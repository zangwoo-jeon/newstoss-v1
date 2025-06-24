package com.newstoss.news.adapter.out.scrap;

import com.newstoss.news.adapter.out.scrap.repository.JpaNewsScrapRepository;
import com.newstoss.news.application.scrap.port.out.ScrapNewsPort;
import com.newstoss.news.domain.NewsScrap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ScrapNewsAdapter implements ScrapNewsPort {
    private final JpaNewsScrapRepository jpaNewsScrapRepository;

    @Override
    public NewsScrap save(NewsScrap newsScrap) {
        return jpaNewsScrapRepository.save(newsScrap);
    }

    @Override
    public boolean delete(UUID scrapNewsId) {
        if (!jpaNewsScrapRepository.existsById(scrapNewsId)) return false;
        jpaNewsScrapRepository.deleteById(scrapNewsId);
        return true;
    }

    @Override
    public List<String> getAll(UUID memberId) {
        return jpaNewsScrapRepository.findByMember_MemberId(memberId).stream()
                .map(NewsScrap::getNewsId)
                .toList();
    }
    
    @Override
    public boolean existsByMemberIdAndNewsId(UUID memberId, String newsId) {
        return jpaNewsScrapRepository.existsByMember_MemberIdAndNewsId(memberId, newsId);
    }

    @Override
    public UUID findNewsScrapId(UUID memberId, String newsId) {
        return jpaNewsScrapRepository.findByMember_MemberIdAndNewsId(memberId, newsId).getScrapNewsId();
    }
}
