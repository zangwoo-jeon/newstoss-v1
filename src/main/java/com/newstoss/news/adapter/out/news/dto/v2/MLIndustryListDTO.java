package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLIndustryListDTO {
    @JsonProperty("stock_id")
    private String stockId;
    @JsonProperty("industry_id")
    private String industryId;
    @JsonProperty("industry_name")
    private String industryName;
}
