package com.newstoss.global.jwt;

import com.newstoss.global.handler.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtResolver jwtResolver;

    // ✅ JWT 검사가 필요한 경로만 지정
    private static final List<String> JWT_REQUIRED_PATHS = List.of(
            "/api/sse/**",
            "/api/news/v2/**",
            "/api/v1/portfolios/**",
            "/api/news/v2/recommend/**",
            "/api/scrap/**"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        return JWT_REQUIRED_PATHS.stream().noneMatch(pattern -> matcher.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            UUID memberId = jwtResolver.extractMemberId(request);
            Authentication authentication = new UsernamePasswordAuthenticationToken(memberId, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("✅ 로그인 사용자: {}", memberId);
        } catch (CustomException e) {
            // ❗ news/sse는 비회원 허용하므로, 로그만 찍고 필터 통과
            if (isOptionalJwtRequest(request)) {
                log.info("❗ 비회원 요청: {}", e.getMessage());
            } else {
                // 🔐 포트폴리오는 필수 → 예외를 그대로 던짐 (Spring이 401/403 처리)
                throw e;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isOptionalJwtRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match("/api/sse/**", uri)
                || (matcher.match("/api/news/v2/**", uri) && !matcher.match("/api/news/v2/recommend/**", uri));
    }
}



