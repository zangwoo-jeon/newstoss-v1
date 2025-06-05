package com.newstoss.portfolio.adapter.inbound.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "누적 Pnl 응답 DTO")
public class MemberPnlAccResponseDto {

    @Schema(description = "누적 pnl 값")
    private Long pnl;
}
