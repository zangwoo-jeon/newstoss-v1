package com.newstoss.news.domain.many;

import com.newstoss.news2.domain.NewsEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "member")
public class MemberScrapTest {
    @Id
    private UUID memberId;

    @ManyToMany
    @JoinTable(
            name = "member_news_scrap_test", // ★ 테스트용 중간 테이블 명
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id")
    )
    private List<NewsEntity> newsList;
}
