package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLRelatedNewsDTOv2 {
    @JsonProperty("news_id")
    private String newsId;
    private String wdate;
    private String title;
    private String press;
    private String url;
    private String image;
    private String summary;
    private double similarity;
    @JsonProperty("stock_list")
    private List<MLRelatedStockDTOv2> stock;
}