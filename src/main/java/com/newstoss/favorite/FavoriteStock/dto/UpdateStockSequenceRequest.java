package com.newstoss.favorite.FavoriteStock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "관심 종목 순서 변경 요청 DTO")
public class UpdateStockSequenceRequest {
    @Schema(description = "종목 코드", example = "000660")
    private String stockCode;

    @Schema(description = "변경할 순서", example = "3")
    private Integer newSequence;
} 