package com.newstoss.portfolio.adapter.inbound.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "포트폴리오 일일 손익 응답 DTO",
        title = "포트폴리오 일일 손익 응답 DTO")
public class PortfolioDailyPnlResponseDto {
    @Schema(description = "포트폴리오 주식 종목 DTO")
    private List<PortfolioStocksResponseDto> portfolioStocks;
    @Schema(description = "포트폴리오 오늘 자산", example = "100000")
    private long totalAsset;

    public PortfolioDailyPnlResponseDto(List<PortfolioStocksResponseDto> portfolioStocks) {
        this.portfolioStocks = portfolioStocks;
        this.totalAsset = portfolioStocks.stream()
                .mapToLong(stock -> (long) stock.getCurrentPrice() * stock.getStockCount())
                .sum();
    }
}
