package com.newstoss.news.domain;

import com.newstoss.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "idx_news_scrap_member_id", columnList = "member_id"),
                @Index(name = "idx_news_scrap_news_id", columnList = "news_id")
        }
)
public class NewsScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID scrapNewsId;
    @ManyToOne(fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @JoinColumn(name = "member_id") // DB 컬럼명과 일치시켜야 함
    private Member member;

    @Column(name = "news_id")
    private String newsId; // 외부 API의 news_id를 직접 저장
}
