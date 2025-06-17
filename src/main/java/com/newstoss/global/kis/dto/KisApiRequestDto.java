package com.newstoss.global.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KisApiRequestDto {
    private String type; // "stock" or "fx"

    @JsonProperty("stockCode")
    private String stockCode;

    @JsonProperty("FxType")
    private String FxType;

    private String FxCode;
}