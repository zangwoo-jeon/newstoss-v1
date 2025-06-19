package com.newstoss.stock.adapter.inbound.dto.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSseDto {
    private String stockCode;
    private String categoryName;
    private String price;
    private String marketName;
    private String changeAmount;
    private String sign;
    private String changeRate;
    private String volume;

    public StockSseDto(String code,KisStockDto kisStockDto) {
        stockCode = code;
        categoryName = kisStockDto.getCategoryName();
        price = kisStockDto.getPrice();
        marketName = kisStockDto.getMarketName();
        changeAmount = kisStockDto.getChangeAmount();
        sign = kisStockDto.getSign();
        changeRate = kisStockDto.getChangeRate();
        volume = kisStockDto.getVolume();
    }

}
