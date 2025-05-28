package com.newstoss.news.adapter.out.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLRelatedStockDTO {
    @JsonProperty("news_id")
    private String newsId;
    private List<StockInfo> stocks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockInfo {
        private String name;
        private String code;
    }
}
