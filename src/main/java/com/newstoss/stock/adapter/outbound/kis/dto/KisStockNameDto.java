package com.newstoss.stock.adapter.outbound.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KisStockNameDto {

    @JsonProperty("prdt_name")
    private String stockName;

    @JsonProperty("prdt_name120")
    private String stockName120;

    @JsonProperty("prdt_abrv_name")
    private String stockNameAbrvName;

    @JsonProperty("prdt_abrv_name120")
    private String stockName120AbrvName;

    @JsonProperty("prdt_clsf_cd")
    private String stockClassifyCd;

    @JsonProperty("prdt_clsf_name")
    private String stockClassifyName;
}
