package com.newstoss.stock.adapter.outbound.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KisStockDto {
    @JsonProperty("bstp_kor_isnm")
    private String categoryName;

    @JsonProperty("rprs_mrkt_kor_name")
    private String marketName;
}
