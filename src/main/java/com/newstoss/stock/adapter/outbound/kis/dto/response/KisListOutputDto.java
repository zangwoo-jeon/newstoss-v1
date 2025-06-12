package com.newstoss.stock.adapter.outbound.kis.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KisListOutputDto<T> {
    private String rt_cd;
    private String msg_cd;
    private String msg1;
    @JsonProperty("output")
    private List<T> output;
}
