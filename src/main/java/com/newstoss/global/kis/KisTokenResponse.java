package com.newstoss.global.kis;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KisTokenResponse {
    private String access_token;
    private String token_type;
    private String access_token_token_expired;
}
