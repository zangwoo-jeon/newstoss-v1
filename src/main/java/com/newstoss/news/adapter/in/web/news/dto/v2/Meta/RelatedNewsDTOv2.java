package com.newstoss.news.adapter.in.web.news.dto.v2.Meta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedNewsDTOv2 {
    private String newsId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime wdate;
    private String title;
    private String article;
    private String url;
    private String press;
    private String image;
    private double similarity;
    @JsonProperty("stock_list")
    private List<RelatedStockDTOv2> stock;
}
