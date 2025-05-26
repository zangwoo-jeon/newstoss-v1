package com.newstoss.stock.adapter.outbound.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KisApiResponseDto<T> {
    @JsonProperty("output")
    private T output;

    @JsonProperty("output1")
    private T output1;

    @JsonProperty("output2")
    private List<T> output2;
}
