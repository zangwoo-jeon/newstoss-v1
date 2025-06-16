package com.newstoss.member.adapter.in.web.dto.response;

import com.newstoss.portfolio.entity.QPortfolioStock;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "유저 정보 조회 DTO")
public class MemberInfoDto {

    @Schema(description = "유저 이름", example = "test")
    private String username;

    @Schema(description = "유저 손익", example = "340000")
    private Long userPnl;

    @Schema(description = "유저 자산", example = "3000000")
    private Long asset;

    @Schema(description = "유저 투자 성향 점수 ", example = "0~25")
    private Long investScore;

    private List<MemberStockDto> memberStocks = new ArrayList<>();

    @QueryProjection
    public MemberInfoDto(String username, Long userPnl) {
        this.username = username;
        this.userPnl = userPnl;
    }
}
