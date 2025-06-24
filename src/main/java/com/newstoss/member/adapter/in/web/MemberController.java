package com.newstoss.member.adapter.in.web;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.member.adapter.in.web.dto.requestDTO.DuplicateDTO;
import com.newstoss.member.adapter.in.web.dto.requestDTO.InvestDto;
import com.newstoss.member.adapter.in.web.dto.requestDTO.SignupRequestDTO;
import com.newstoss.member.adapter.in.web.dto.requestDTO.WithdrawDTO;
import com.newstoss.member.application.MemberService;
import com.newstoss.member.domain.Member;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("/api/auth")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Object>> signup(@RequestBody SignupRequestDTO requestDTO){
        Member member= memberService.signup(requestDTO);
        return ResponseEntity.ok(new SuccessResponse<>(true, "회원가입 성공", null));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse<Object>> withdraw(@RequestBody WithdrawDTO withdrawDTO){
        memberService.withdraw(withdrawDTO.getMemberId());
        return ResponseEntity.ok(new SuccessResponse<>(true, "회원탈퇴 성공", null));
    }

    @PostMapping("/duplicate")
    public ResponseEntity<SuccessResponse<Object>> duplicateCheck(@RequestBody DuplicateDTO duplicateDTO){
        boolean success = memberService.duplicateCheck(duplicateDTO.getAccount());
        return ResponseEntity.ok(new SuccessResponse<>(true, "중복된 아이디 없음", null));
    }

    @GetMapping("/fgoffset")
    public ResponseEntity<SuccessResponse<Object>> fgSet(@RequestParam UUID memberId, @RequestParam UUID fgOffset){
        memberService.fgOffset(memberId,fgOffset);
        return ResponseEntity.ok(new SuccessResponse<>(true, "현재 보고 있는 관심 그룹 변경 완료", null));
    }

    @PostMapping("/invest")
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

