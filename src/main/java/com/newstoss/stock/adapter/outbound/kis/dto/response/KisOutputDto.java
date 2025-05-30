package com.newstoss.stock.adapter.outbound.kis.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KisOutputDto<T> {

    @JsonProperty("output")
    private T output;
}
