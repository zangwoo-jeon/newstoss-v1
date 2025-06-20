package com.newstoss.global.jwt;


import com.newstoss.global.errorcode.JwtErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.domain.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {
    private final Key secretKey;
    private static final long ACCESS_EXPIRATION = 3600000;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Member member) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_EXPIRATION);

        return Jwts.builder()
                .setSubject("ACCESS_TOKEN")
                .setSubject("ACCESS_TOKEN")
                .claim("memberId", member.getMemberId())
                .claim("memberName", member.getName())
                .claim("phoneNumber", member.getPhoneNumber())
                .claim("email", member.getEmail())
                .claim("invest", member.getInvestScore())
                .claim("zipCode", member.getAddress().getZipcode())
                .claim("Address", member.getAddress().getAddress())
                .claim("AddressDetail", member.getAddress().getAddressDetail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    public UUID getMemberId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return UUID.fromString(claims.get("memberId", String.class));
    }
}
