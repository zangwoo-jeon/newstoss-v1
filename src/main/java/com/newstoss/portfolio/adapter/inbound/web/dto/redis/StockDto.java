package com.newstoss.portfolio.adapter.inbound.web.dto.redis;

import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
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

    public StockDto(String name, String code, KisStockDto dto) {
        stockCode = code;
        stockName = name;
        categoryName = dto.getCategoryName();
        price = dto.getPrice();
        openPrice = dto.getOpenPrice();
        highPrice = dto.getHighPrice();
        lowPrice = dto.getLowPrice();
        marketName = dto.getMarketName();
        changeAmount = dto.getChangeAmount();
        sign = dto.getSign();
        changeRate = dto.getChangeRate();
        volume = dto.getVolume();
        volumeValue = dto.getVolumeValue();
    }
}

