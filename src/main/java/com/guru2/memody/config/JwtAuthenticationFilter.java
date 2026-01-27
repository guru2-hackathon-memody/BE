package com.guru2.memody.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    // 필터 로직 수행
    // 헤더에서 토큰 추출, 유효할 시 인증 정보 생성 및 저장
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // 인증이 필요없는 경로는 검사 X(최적화)
        if (path.startsWith("/auth") || path.startsWith("/location/") ||
                path.startsWith("/h2-console") || path.startsWith("/uploads/images")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 인증이 필요한 경우 JWT 검증
        // 토큰 추출
        String token = resolveToken(request);

        // 토큰 유효성 검증 및 인증 처리
        if (token != null && jwtTokenProvider.validateJwtToken(token)) {
            String email = jwtTokenProvider.getEmailFromJwtToken(token);

            // DB에서 상세 유저 정보 조회
            CustomUserDetails userDetails =
                    (CustomUserDetails) userDetailsService.loadUserByUsername(email);

            // 인증 객체 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }



    // 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("[AUTH HEADER RAW] " + bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            System.out.println("[TOKEN ONLY] " + token);
            return token;
        }
        return null;
    }




}