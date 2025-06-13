package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLHighlightNewsDTOv2 {
    @JsonProperty("news_id")
    private String newsId;
    private String wdate;
    private String title;
    private String image;
    private String press;
    private String summary;
    @JsonProperty("impact_score")
    private double impactScore;
}