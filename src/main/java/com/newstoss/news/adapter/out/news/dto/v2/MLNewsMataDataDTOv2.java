package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLNewsMataDataDTOv2 {
    @JsonProperty("news_id")
    private String newsId;
    private String summary;
    @JsonProperty("stock_list")
    private List<String> stockList;
    @JsonProperty("industry_list")
    private List<String> industryList;
    @JsonProperty("stock_list_view")
    private List<String> stockViewList;
}
