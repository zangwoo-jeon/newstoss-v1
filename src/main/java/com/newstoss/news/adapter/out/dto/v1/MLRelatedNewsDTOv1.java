package com.newstoss.news.adapter.out.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLRelatedNewsDTOv1 {
    @JsonProperty("news_id")
    private String newsId;
    private Date date;
    private String title;
    private String content;
    private String url;
    private double similarity;
}