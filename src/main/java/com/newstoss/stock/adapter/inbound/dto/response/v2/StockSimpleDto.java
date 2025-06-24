package com.newstoss.stock.adapter.inbound.dto.response.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카테고리별 주식 간단 정보 DTO")
public class StockSimpleDto {

    @Schema(description = "주식 이름")
    private String StockName;

    @Schema(description = "주식 코드")
    private String StockCode;
}
