package com.newstoss.news.adapter.in.web.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedStockDTOv2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private String summary;
    @JsonProperty("stock_list")
    private List<RelatedStockDTOv2> stock;
    private String press;
    private String url;
    private String image;
    @JsonProperty("impact_score")
    private double impactScore;
    @JsonProperty("news_count_total")
    private String newsCountTotal;
    @JsonProperty("news_count_today")
    private String newsCountToday;
}
