package com.newstoss.news.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntity {
    @Id
    private String newsId;
    private LocalDateTime wdate;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String article;
    private String url;
    private String press;
    private String image;
}