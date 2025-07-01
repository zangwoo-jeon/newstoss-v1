package com.newstoss.auth.adapter.in.web;

import com.newstoss.auth.adapter.in.web.dto.requestDTO.LoginDTO;
import com.newstoss.auth.application.AuthService;
import com.newstoss.global.jwt.JwtResolver;
import com.newstoss.global.response.SuccessResponse;
import com.zaxxer.hikari.util.SuspendResumeLock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@Tag(name = "AUTH API", description = "인증과 관련되 api입니다.")
public class AuthController {
    private final AuthService authService;
    private final JwtResolver jwtResolver;

    @PostMapping("/login")
    @Operation(summary = "로그인 api", description = "로그인을 진행합니다.")
    public ResponseEntity<SuccessResponse<Object>> login(@RequestBody LoginDTO request, HttpServletResponse response) {
        String jwt = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("accessToken", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("None") // 핵심
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok(new SuccessResponse<>(true, "로그인 성공", null));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 api", description = "로그아웃 진행합니다.")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/refresh")
    @Operation(summary = "토큰 재발급 api", description = "토큰을 재발급 합니다.")
    public ResponseEntity<SuccessResponse<Object>> refresh(HttpServletResponse response, HttpServletRequest request) {
        String token = authService.token(request);
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("None") // 핵심
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok(new SuccessResponse<>(true, "토큰 재발급 성공", token));
    }
}
