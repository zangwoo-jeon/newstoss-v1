package com.newstoss.global.healthcheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class MLHealthCheck {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://3.37.207.16:8000/healthcheck";

    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthcheck() {
        try {
            // ML API에 실제로 GET 요청 보내봄
            ResponseEntity<String> mlResponse = restTemplate.getForEntity(BASE_URL, String.class);
            if (mlResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok("ML API OK");
            } else {
                return ResponseEntity.status(503).body("ML API Unhealthy");
            }
        } catch (Exception e) {
            return ResponseEntity.status(503).body("ML API Unreachable: " + e.getMessage());
        }
    }
}