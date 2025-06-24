package com.newstoss.news.adapter.in.web.news.dto.v2.Meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryListDTO {
    @JsonProperty("stock_id")
    private String stockId;
    @JsonProperty("industry_id")
    private String industryId;
    @JsonProperty("industry_name")
    private String industryName;
}
