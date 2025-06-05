package com.newstoss.portfolio.adapter.inbound.web.api;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.portfolio.adapter.inbound.web.dto.MemberPnlAccResponseDto;
import com.newstoss.portfolio.adapter.inbound.web.dto.response.MemberPnlPeriodResponseDto;
import com.newstoss.portfolio.application.port.in.GetMemberPnlAccUseCase;
import com.newstoss.portfolio.application.port.in.GetMemberPnlPeriodUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "손익 API", description = "Pnl 관련 API")
@RequestMapping("/api/v1/portfolios/asset")
public class PnlApiController {

    private final GetMemberPnlPeriodUseCase memberPnlPeriodUseCase;
    private final GetMemberPnlPeriodUseCase getMemberPnlPeriodUseCase;
    private final GetMemberPnlAccUseCase getMemberPnlAccUseCase;

    @Operation(
            summary = "포트폴리오 손익 기간 조회",
            description = "특정 사용자의 포트폴리오 손익을 특정 기간 동안 조회합니다. " +
                    "기간은 일별, 월별 등의 형식으로 입력받습니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "포트폴리오 손익 기간 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberPnlPeriodResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류"
                    )
            }
    )

    @GetMapping("{memberId}")
    public ResponseEntity<?> getMemberPnlPeriod(@PathVariable("memberId") UUID memberId,
                                                @RequestParam String period) {
        log.info("getMemberPnlPeriod called");
        MemberPnlPeriodResponseDto memberPnlPeriod = memberPnlPeriodUseCase.getMemberPnlPeriod(memberId, period);
        return ResponseEntity.ok(new SuccessResponse<>(true, "포트폴리오 손익 기간 조회 성공", memberPnlPeriod));

    }
    @Operation(
            summary = "포트폴리오 손익 기간 조회",
            description = "특정 사용자의 포트폴리오 손익을 특정 기간 동안 조회합니다. " +
                    "기간은 일별, 월별 등의 형식으로 입력받습니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "포트폴리오 손익 기간 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberPnlAccResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류"
                    )
            }
    )
    @GetMapping("/pnl/{memberId}")
    public ResponseEntity<?> getMemberPnl(@PathVariable("memberId") UUID memberId,
                                          @RequestParam String period) {
        log.info("getMemberPnl called for memberId: {}", memberId);
        Long acc = getMemberPnlAccUseCase.getMemberPnlAcc(memberId, period);
        MemberPnlAccResponseDto dto = new MemberPnlAccResponseDto(acc);
        return ResponseEntity.ok(new SuccessResponse<>(true, "포트폴리오 손익 조회 성공",dto));
    }

}
