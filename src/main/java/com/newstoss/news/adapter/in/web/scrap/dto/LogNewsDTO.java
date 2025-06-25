package com.newstoss.news.adapter.in.web.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogNewsDTO {
    private String news_id;
    private String wdate;
    private String title;
    private String article;
    private String url;
    private String press;
    private String image;
}