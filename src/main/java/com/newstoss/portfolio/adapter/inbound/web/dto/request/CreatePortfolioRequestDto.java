package com.newstoss.portfolio.adapter.inbound.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePortfolioRequestDto {

    @JsonProperty("stock_code")
    private String stockCode;

    @JsonProperty("stock_count")
    private Integer stockCount;

    @JsonProperty("entry_price")
    private Integer entryPrice;

}
