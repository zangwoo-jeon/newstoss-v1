package com.newstoss.favorite.adapter.out.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExternalStockApiResponse {
    @JsonProperty("Success")
    private boolean success;
    private String message;
    private StockData data;
    
    @Data
    public static class StockData {
        private String stockCode;
        private String stockName;
        private String categoryName;
        private String price;
        private String openPrice;
        private String highPrice;
        private String lowPrice;
        private String marketName;
        private String changeAmount;
        private String sign;
        private String changeRate;
        private String volume;
        private String volumeValue;
        private String stockImage;
    }
} 