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

    @Schema(description = "손익 이력")
    private List<MemberPnl> pnlHistory; // 손익 이력

    @Schema(description = "과거 자산", example = "100000")
    private Long PeriodAsset; // 과거 자산



}
