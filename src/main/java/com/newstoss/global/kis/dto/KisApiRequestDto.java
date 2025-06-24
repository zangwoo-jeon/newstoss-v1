package com.newstoss.global.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KisApiRequestDto {
    private String type; // "stock" or "fx"
    private Map<String, String> payload;
}