package com.newstoss.member.adapter.in.web;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.member.adapter.in.web.dto.requestDTO.SignupRequestDTO;
import com.newstoss.member.application.MemberService;
import com.newstoss.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

//@Tag(name = "알람 API", description = "알람 관련 API")
@RequestMapping("/api/auth")
@CrossOrigin("*")
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
    public ResponseEntity<SuccessResponse<Object>> withdraw(@RequestBody UUID memberId){
        memberService.withdraw(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "회원탈퇴 성공", null));
    }
}
