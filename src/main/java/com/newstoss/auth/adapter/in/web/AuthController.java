package com.newstoss.auth.adapter.in.web;

import com.newstoss.auth.adapter.in.web.dto.requestDTO.LoginDTO;
import com.newstoss.auth.application.AuthService;
import com.newstoss.global.response.SuccessResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
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
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
