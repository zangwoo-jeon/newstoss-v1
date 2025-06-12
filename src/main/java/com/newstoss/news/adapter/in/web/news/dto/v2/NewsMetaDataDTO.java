package com.newstoss.news.adapter.in.web.news.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsMetaDataDTO {
    private String newsId;
    private String summary;
    private List<String> stockList;
    private List<String> industryList;
    private List<String> stockListView;
}
