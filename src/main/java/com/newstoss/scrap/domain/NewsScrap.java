package com.newstoss.scrap.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.connection.stream.StreamInfo;

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
    private String newsId;
}
