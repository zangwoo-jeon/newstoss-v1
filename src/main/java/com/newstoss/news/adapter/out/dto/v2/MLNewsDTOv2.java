package com.newstoss.news.adapter.out.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLNewsDTOv2 {
    @JsonProperty("news_id")
    private String newsId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("wdate")
    private LocalDateTime wdate;
    private String title;
    private String article;
    private String url;
    private String press;
    private String image;
}