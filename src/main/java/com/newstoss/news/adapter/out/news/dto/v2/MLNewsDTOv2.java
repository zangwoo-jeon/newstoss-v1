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
public class MLNewsDTOv2 {
    @JsonProperty("news_id")
    private String newsId;
    private String wdate;
    private String title;
    private String article;
    private String url;
    private String press;
    private String image;
    @JsonProperty("stock_list")
    private List<MLRelatedStockDTOv2> stock;
}