package com.newstoss.news.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsScrap {
    @Id
    private UUID scrapNewsId;
    private UUID memberId;
    private UUID newsId;
}
