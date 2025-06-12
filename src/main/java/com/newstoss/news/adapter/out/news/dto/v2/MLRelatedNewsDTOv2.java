package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLRelatedNewsDTOv2 {
    @JsonProperty("news_id")
    private String newsId;
    private String wdate;
    private String title;
    private String article;
    private String url;
    private String press;
    private String image;
    private double similarity;
}