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

    @JsonProperty("stck_prpr")
    private String price;

    @JsonProperty("stck_hgpr")
    private String highPrice;

    @JsonProperty("stck_lwpr")
    private String lowPrice;

    @JsonProperty("stck_oprc")
    private String openPrice;

    @JsonProperty("rprs_mrkt_kor_name")
    private String marketName;

    @JsonProperty("prdy_vrss")
    private String changeAmount;

    @JsonProperty("prdy_vrss_sign")
    private String sign;

    @JsonProperty("prdy_ctrt")
    private String changeRate;

    @JsonProperty("acml_vol")
    private String volume;

    @JsonProperty("acml_tr_pbmn")
    private String volumeValue;

    @JsonProperty("hts_avls")
    private String capital;
}
