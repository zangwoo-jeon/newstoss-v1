package com.newstoss.news.adapter.in.web.sse.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatStreamResponse {
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("is_last")
    private boolean isLast;

    @JsonProperty("index")
    private Integer index;
}