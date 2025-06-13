package com.newstoss.portfolio.adapter.inbound.web.dto.response;

import com.newstoss.portfolio.entity.PortfolioStock;
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

    @Schema(description = "주식 이미지 url")
    private String stockImage;

    @Schema(description = "주식 코드", example = "005930")
    private String stockCode;

    @Schema(description = "주식 수량", example = "10")
    private Integer stockCount;

    @Schema(description = "매입 단가", example = "50000")
    private Integer entryPrice;

    @Schema(description = "현재 주가", example = "60000")
    private Integer currentPrice;

    @Schema(description = "손익", example = "100000")
    private Long profitLoss;

    @Schema(description = "손익률", example = "20.0")
    private Double profitLossRate;



    public PortfolioStocksResponseDto(PortfolioStock portfolioStock) {
        this.stockName = portfolioStock.getStock().getName();
        this.stockImage = portfolioStock.getStock().getStockImage();
        this.stockCode = portfolioStock.getStock().getStockCode();
        this.stockCount = portfolioStock.getStockCount();
        this.entryPrice = portfolioStock.getEntryPrice();
    }

    public void updatePrices(Integer currentPrice, Long profitLoss , Double profitLossRate) {
        this.currentPrice = currentPrice;
        this.profitLoss = profitLoss;
        this.profitLossRate = profitLossRate;
    }
}
