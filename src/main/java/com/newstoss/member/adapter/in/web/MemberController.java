package com.newstoss.member.adapter.in.web;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.member.adapter.in.web.dto.requestDTO.DuplicateDTO;
import com.newstoss.member.adapter.in.web.dto.requestDTO.InvestDto;
import com.newstoss.member.adapter.in.web.dto.requestDTO.SignupRequestDTO;
import com.newstoss.member.adapter.in.web.dto.requestDTO.WithdrawDTO;
import com.newstoss.member.application.MemberService;
import com.newstoss.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("/api/auth")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@Tag(name = "멤버 API", description = "회원가입, 탈퇴 등 멤버 정보와 관련되 API입니다.")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/register")
    @Operation(summary = "회원 가입 api", description = "입력한 유저 정보를 바탕으로 회원가입을 진행합니다.")
    public ResponseEntity<SuccessResponse<Object>> signup(@Valid @RequestBody SignupRequestDTO requestDTO){
        Member member= memberService.signup(requestDTO);
        return ResponseEntity.ok(new SuccessResponse<>(true, "회원가입 성공", null));
    }

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원 탈퇴 api", description = "회원 탈퇴를 진행합니다.")
    public ResponseEntity<SuccessResponse<Object>> withdraw(@RequestBody WithdrawDTO withdrawDTO){
        memberService.withdraw(withdrawDTO.getMemberId());
        return ResponseEntity.ok(new SuccessResponse<>(true, "회원탈퇴 성공", null));
    }

    @PostMapping("/duplicate")
    @Operation(summary = "중복 ID 체크 api", description = "회원가입 시 중복된 ID가 있는지 확인합니다.")
    public ResponseEntity<SuccessResponse<Object>> duplicateCheck(@RequestBody DuplicateDTO duplicateDTO){
        boolean success = memberService.duplicateCheck(duplicateDTO.getAccount());
        return ResponseEntity.ok(new SuccessResponse<>(true, "중복된 아이디 없음", null));
    }

    @GetMapping("/fgoffset")
    @Operation(summary = "관심 그룹 설정 api", description = "현재 보고 있는 관심 그룹을 설정합니다.")
    public ResponseEntity<SuccessResponse<Object>> fgSet(@RequestParam UUID memberId, @RequestParam UUID fgOffset){
        memberService.fgOffset(memberId,fgOffset);
        return ResponseEntity.ok(new SuccessResponse<>(true, "현재 보고 있는 관심 그룹 변경 완료", null));
    }

    @PostMapping("/invest")
    @Operation(summary = "투자 성향 설정 api", description = "회원가입 시 진행한 설문조사를 기반으로 투자성향을 저장합니다.")
    public ResponseEntity<SuccessResponse<Object>> invest(@RequestBody InvestDto investDto, HttpServletResponse response){
        String jwt = memberService.invest(investDto.getMemberId(), investDto.getInvestScore());
        ResponseCookie cookie = ResponseCookie.from("accessToken", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("None") // 핵심
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok(new SuccessResponse<>(true, "멤버 투자 성향 수정 완료", null));
    }
}

