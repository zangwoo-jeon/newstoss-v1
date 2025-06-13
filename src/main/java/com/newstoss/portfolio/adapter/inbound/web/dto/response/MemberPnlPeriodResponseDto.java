package com.newstoss.portfolio.adapter.inbound.web.dto.response;

import com.newstoss.portfolio.entity.MemberPnl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "포트폴리오 손익 기간 응답 DTO",
        title = "포트폴리오 손익 기간 응답 DTO")
public class MemberPnlPeriodResponseDto {
    @Schema(description = "총 자산", example = "100000")
    private Long todayAsset; // 오늘 자산

    @Schema(description = "오늘 손익", example = "100000")
    private Long todayPnl; // 오늘 손익

    @Schema(description = "손익 이력")
    private List<MemberPnl> pnlHistory; // 손익 이력

    @Schema(description = "기간별 손익", example = "100000")
    private Long PeriodPnl;

    @Schema(description = "기간별 손익 변화량", example = "+25%")
    private Double PnlPercent;



}
