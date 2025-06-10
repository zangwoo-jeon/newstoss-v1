package com.newstoss.news.adapter.out.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLRelatedStockDTOv1 {
    @JsonProperty("news_id")
    private String newsId;
    private List<String> stocks;
//    private List<StockInfo> stocks;

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class StockInfo {
//        private String name;
//        private String code;
//    }
}
