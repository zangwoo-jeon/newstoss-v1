package com.newstoss.news.adapter.in.web.sse.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ChatStreamRequest(
        String client_id,
        @JsonProperty("message")
        String message
) {}