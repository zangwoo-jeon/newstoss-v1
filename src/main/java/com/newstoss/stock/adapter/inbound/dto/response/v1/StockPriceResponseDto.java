package com.newstoss.stock.adapter.inbound.dto.response.v1;

import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "주식 가격 응답 DTO")
public class StockPriceResponseDto {

    @Schema(description = "주식 날짜", example = "20230330")
    private String stockDate; // 주식 날짜

    @Schema(description = "주식 가격", example = "12345")
    private String stockPrice;

    @Schema(description = "주식 시가", example = "12000")
    private String stockOpenPrice; // 주식 시가

    @Schema(description = "주식 고가", example = "12500")
    private String stockHighPrice; // 주식 고가

    @Schema(description = "주식 저가", example = "11900")
    private String stockLowPrice;

    @Schema(description = "주식 전일 종가", example = "12200")
    private String stockPrevPrice; // 주식 전일 종가

    @Schema(description = "전일 종가 부호", example = "+")
    private String PrevSign; // 전일 종가 부호

    @Schema(description = "누적 거래량", example = "1000000")
    private String AccumulatedVolume; // 누적 거래량

    @Schema(description = "누적 거래대금", example = "5000000000")
    private String AccumulatedTradeAmount; // 누적 거래대금

    public StockPriceResponseDto(KisPeriodStockDto kisPeriodStockDto) {
        this.stockDate = kisPeriodStockDto.getStockDate();
        this.stockPrice = kisPeriodStockDto.getStockPrice();
        this.stockOpenPrice = kisPeriodStockDto.getStockOpenPrice();
        this.stockHighPrice = kisPeriodStockDto.getStockHighPrice();
        this.stockLowPrice = kisPeriodStockDto.getStockLowPrice();
        this.stockPrevPrice = kisPeriodStockDto.getPreviousPrice();
        this.PrevSign = kisPeriodStockDto.getPreviousPriceSign();
        this.AccumulatedVolume = kisPeriodStockDto.getAccumulatedVolume();
        this.AccumulatedTradeAmount = kisPeriodStockDto.getAccumulatedTradeAmount();

    }


}
