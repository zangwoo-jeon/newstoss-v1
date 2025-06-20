package com.newstoss.auth.adapter.in.web;

import com.newstoss.auth.adapter.in.web.dto.requestDTO.LoginDTO;
import com.newstoss.auth.application.AuthService;
import com.newstoss.global.response.SuccessResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
        Cookie cookie = new Cookie("accessToken", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 1시간
        response.addCookie(cookie);
        response.setHeader("Set-Cookie",
                String.format("accessToken=%s; Max-Age=3600; Path=/; HttpOnly; Secure; SameSite=None", jwt));
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
