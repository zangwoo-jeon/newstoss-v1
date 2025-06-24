package com.newstoss.favorite.FavoriteStock.dto;

import com.newstoss.stock.adapter.inbound.dto.response.v1.SearchResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "관심종목 응답 DTO")
public class FavoriteStockResponseDto {
    @Schema(description = "주식 정보")
    private SearchResponseDto stockInfo;

    @Schema(description = "관심종목 순서")
    private Integer stockSequence;
} 