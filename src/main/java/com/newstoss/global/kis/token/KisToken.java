package com.newstoss.global.kis.token;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KisToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500)
    private String token;

    private LocalDateTime expireAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt.minusSeconds(30)); // 30초 여유
    }
    public static KisToken createToken(KisTokenResponse response) {
        KisToken newToken = new KisToken();
        newToken.token = response.getAccess_token();
        newToken.expireAt = LocalDateTime.parse(response.getAccess_token_token_expired(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return newToken;
    }
    public String changeToken(KisTokenResponse response) {
        this.token = response.getAccess_token();
        this.expireAt = LocalDateTime.parse(response.getAccess_token_token_expired(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return this.token;
    }
}
