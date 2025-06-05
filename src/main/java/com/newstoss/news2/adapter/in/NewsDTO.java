package com.newstoss.news2.adapter.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    @JsonProperty("news_id")
    private String newsId;
    private LocalDateTime wdate;
    private String title;
    private String article;
    private String url;
    private String press;
    private String image;
}