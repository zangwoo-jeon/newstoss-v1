package com.newstoss.news.adapter.in.web.news.dto.v2.Meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedStockDTOv2 {
    @JsonProperty("stock_id")
    private String stockId;
    @JsonProperty("stock_name")
    private String stockName;
}
