package com.newstoss.news.domain;

import com.newstoss.member.domain.Member;
import com.newstoss.news2.domain.NewsEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsScrap {
    @Id
    private UUID scrapNewsId;
    @ManyToOne(fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @JoinColumn(name = "member_id") // DB 컬럼명과 일치시켜야 함
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    @BatchSize(size = 100)
    private NewsEntity news;
}
