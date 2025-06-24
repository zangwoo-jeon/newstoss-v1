package com.newstoss.stock.adapter.inbound.dto.response.v1;

import com.newstoss.stock.adapter.outbound.kis.dto.KisIndiceInfoDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndicePriceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "주요 지수 일자별 조회 응답 DTO")
public class IndicesResponseDto {

    @Schema(description = "이전 거래일 종가", example = "3000.00")
    private String prev;

    @Schema(description = "이전 거래일 대비 상승/하락 표시", example = "+")
    private String sign;

    @Schema(description = "이전 거래일 대비 상승/하락 비율", example = "0.50")
    private String prev_rate;

    @Schema(description = "지수 가격 정보 리스트")
    private List<KisIndicePriceDto> indices;

    public IndicesResponseDto(KisIndiceInfoDto prevInfo, List<KisIndicePriceDto> indices) {
        prev = prevInfo.getPrev();
        sign = prevInfo.getSign();
        prev_rate = prevInfo.getPrev_rate();
        this.indices = indices;
    }
}
