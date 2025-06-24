package com.newstoss.news.adapter.in.web.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealTimeNewsDTO {
    @JsonProperty("news_id")
    private String newsId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime wdate;
    private String title;
    private String article;
    private String press;
    private String url;
    private String image;
    private double impact_score;
    @JsonProperty("news_count_total")
    private String newsCountTotal;
    @JsonProperty("news_count_today")
    private String newsCountToday;
}
