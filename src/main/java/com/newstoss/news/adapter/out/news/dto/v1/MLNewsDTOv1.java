package com.newstoss.news.adapter.out.news.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLNewsDTOv1 {
    @JsonProperty("news_id")
    private String newsId;
    private Date date;
    private String title;
    private String url;
    private String content;
}
