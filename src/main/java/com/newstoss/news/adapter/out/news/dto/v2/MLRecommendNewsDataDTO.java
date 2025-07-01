package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLRecommendNewsDataDTO {
    @JsonProperty("news_id")
    private String newsId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime wdate;
    private String title;
    private String summary;
    private String image;
    private String press;
    private String url;
    @JsonProperty("click_score")
    private String clickScore;
    @JsonProperty("recommend_reasons")
    private List<String> recommendReasons;
    @JsonProperty("stock_list")
    private List<MLRelatedStockDTOv2> stock;

}
