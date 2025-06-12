package com.newstoss.news.adapter.in.web.news.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTOv1 {
    private String newsId;
    private String title;
    private String url;
    private String content;
}
