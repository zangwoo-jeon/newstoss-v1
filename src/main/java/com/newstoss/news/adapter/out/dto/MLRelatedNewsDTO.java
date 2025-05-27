package com.newstoss.news.adapter.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLRelatedNewsDTO {
    @JsonProperty("news_id")
    private String newsId;
    private Date date;
    private String title;
    private String content;
    private String url;
    private Long similarity;
}
//  {
//          "news_id": "string",
//          "date": "2025-05-27",
//          "title": "string",
//          "content": "string",
//          "url": "string",
//          "similarity": 0
//          }
