package com.guru2.memody.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final long EXPIRATION_TIME = 3600 * 1000L; //토큰유지 1시간


    // 토큰 생성 (이메일을 가지고 JWT access token 생성)
    public String generateJwtToken(String email) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject(email) // 식별자
                .setIssuedAt(now) // 발행 시간
                .setExpiration(expirationDate) // 만료 시간
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256) // 서명 알고리즘
                .compact();
        return token;
    }

    // 토큰에서 이메일 추출
    // 암호화된 토큰 복호화 및 이메일 추출
    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 유효성 검증
    // 만료, 변조, 형식 오류 등 종합적 검증
    public boolean validateJwtToken(String token) {
        try {
            System.out.println("[VALIDATE TOKEN] " + token);

            // 실제 검증(서명 키를 통해 시도)
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);

            System.out.println("[JWT VALID] Token is valid.");
            return true;

            // 토큰 예외처리
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("[JWT ERROR] Token expired: " + e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("[JWT ERROR] Invalid signature: " + e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("[JWT ERROR] Malformed token: " + e.getMessage());
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            System.out.println("[JWT ERROR] Unsupported token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[JWT ERROR] Empty or null token: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[JWT ERROR] Unknown error: " + e.getMessage());
        }

        System.out.println("[JWT INVALID] Token validation failed.");
        return false;
    }

}