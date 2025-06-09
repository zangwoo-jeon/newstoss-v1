package com.newstoss.portfolio.adapter.inbound.web.dto.response;

import com.newstoss.portfolio.entity.Portfolio;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(
    description = "유저의 포트폴리오에 포함된 주식 목록 응답 DTO",
    title = "포트폴리오 주식 목록 응답 DTO")
public class PortfolioStocksResponseDto {
    @Schema(description = "주식 이름" , example = "삼성전자")
    private String stockName;

    @Schema(description = "주식 코드", example = "005930")
    private String stockCode;

    @Schema(description = "주식 수량", example = "10")
    private Integer stockCount;

    @Schema(description = "매입 단가", example = "50000")
    private Integer entryPrice;

    @Schema(description = "현재 주가", example = "60000")
    private Integer currentPrice;

    @Schema(description = "손익", example = "100000")
    private Integer profitLoss;

    @Schema(description = "손익률", example = "20.0")
    private Double profitLossRate;



    public PortfolioStocksResponseDto(Portfolio portfolio) {
        this.stockName = portfolio.getStock().getName();
        this.stockCode = portfolio.getStock().getStockCode();
        this.stockCount = portfolio.getStockCount();
        this.entryPrice = portfolio.getEntryPrice();
    }
}
