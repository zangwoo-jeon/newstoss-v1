package com.newstoss.news.adapter.in.web.sse.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatStreamRequest {
        @JsonProperty("client_id")
        String clientId;
        @JsonProperty("question")
        String question;
}