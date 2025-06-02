package com.newstoss.scrap.adapter.out;

import com.newstoss.scrap.application.port.out.ScrapNewsPort;
import com.newstoss.scrap.domain.NewsScrap;
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
        return jpaNewsScrapRepository.findByMemberId(memberId).stream()
                .map(NewsScrap::getNewsId)
                .toList();
    }
    @Override
    public boolean existsByMemberIdAndNewsId(UUID memberId, String newsId) {
        return jpaNewsScrapRepository.existsByMemberIdAndNewsId(memberId, newsId);
    }

    @Override
    public UUID findNewsScrapId(UUID memberId, String newsId) {
        return jpaNewsScrapRepository.findByMemberIdAndNewsId(memberId, newsId).getScrapNewsId();
    }
}
