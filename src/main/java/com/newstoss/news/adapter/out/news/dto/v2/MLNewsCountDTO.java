package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLNewsCountDTO {
    @JsonProperty("news_count_total")
    private Long newsCountTotal;
    @JsonProperty("news_count_today")
    private Long newsCountToday;
}
