package com.newstoss.member.adapter.in.web.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "유저 주식 정보 DTO")
public class MemberStockDto {

    @Schema(description = "주식 코드" , example = "005930")
    private String stockCode;

    @Schema(description = "주식 이름", example = "삼성전자")
    private String stockName;

    @Schema(description = "주식 손익", example = "100000")
    private Long pnl;


    @QueryProjection
    public MemberStockDto(String stockCode, String stockName , Long pnl) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.pnl = pnl;
    }
}
