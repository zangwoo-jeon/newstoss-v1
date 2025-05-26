package com.newstoss.stock.adapter.outbound.kis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KisIndicePriceDto {
    @JsonProperty("-BSTP_NMIX_PRDY_VRSS")
    private String prev;

    @JsonProperty("-PRDY_VRSS_SIGN")
    private String sign;

    @JsonProperty("-BSTP_NMIX_PRDY_VRSS")
    private String prev_rate;

    @JsonProperty("-BSTP_NMIX_PRPR")
    private String cur_price;

    @JsonProperty("-BSTP_NMIX_HGPR")
    private String high_price;

    @JsonProperty("-BSTP_NMIX_LWPR")
    private String low_price;

    @JsonProperty("-ACML_VOL")
    private String acml_vol;

    @JsonProperty("-ACML_TR_PBMN")
    private String acml_vol_price;
}
