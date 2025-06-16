package com.newstoss.news.adapter.in.web.sse.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChatStreamResponse(
        @JsonProperty("client_id") String clientId,
        @JsonProperty("message") String message,
        @JsonProperty("is_last") boolean isLast
) {}