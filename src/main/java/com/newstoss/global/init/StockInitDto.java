package com.newstoss.global.init;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInitDto {

    @JsonProperty("corp_name")
    private String name;

    @JsonProperty("stock_code")
    private String stockCode;


}
