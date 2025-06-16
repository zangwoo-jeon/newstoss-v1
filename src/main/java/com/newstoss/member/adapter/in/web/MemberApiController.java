package com.newstoss.member.adapter.in.web;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.member.adapter.in.web.dto.response.MemberInfoDto;
import com.newstoss.member.application.in.query.GetMemberInfoUseCase;
import com.newstoss.portfolio.adapter.inbound.web.dto.MemberPnlAccResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/userinfo")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "유저 정보 조회 API", description = "유저 정보를 조회합니다. 혹시 더 필요한 정보 있으시면 말씀해주세요!")
public class MemberApiController {

    private final GetMemberInfoUseCase getMemberInfoUseCase;

    @Operation(
            summary = "유저 정보 조회",
            description = "유저 정보를 조회합니다. " +
                    "유저 이름, 유저 포트폴리오 pnl, 유저 포트폴리오 자산, 유저가 가지고 있는 주식정보를 응답으로 보냅니다.<br>",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "유저 정보 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberInfoDto.class)
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
    @Parameter(name = "memberId", example = "870fc0b2-c3b1-4dce-bcbb-f20d4ca0b054")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> userInfo(@PathVariable UUID memberId) {
        log.info("유저 정보 조회");
        MemberInfoDto memberInfo = getMemberInfoUseCase.getMemberInfo(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "유저 정보 조회에 성공하였습니다.", memberInfo));
    }
}
