package com.newstoss.global.jwt;

import com.newstoss.global.errorcode.JwtErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.application.in.query.GetMemberService;
import com.newstoss.member.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final GetMemberService getMemberService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromCookie(request);

        if (token != null && jwtProvider.validateToken(token)) {
            UUID memberId = jwtProvider.getMemberId(token);
            Member member = getMemberService.findById(memberId);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(member, null, List.of()); // 권한 필요시 추가

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public UUID extractMemberIdFromToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) throw new CustomException(JwtErrorCode.MISSING_TOKEN);

        String token = Arrays.stream(cookies)
                .filter(c -> "access_token".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new CustomException(JwtErrorCode.MISSING_TOKEN));

        jwtProvider.validateToken(token);

        return jwtProvider.getMemberId(token);
    }
}


