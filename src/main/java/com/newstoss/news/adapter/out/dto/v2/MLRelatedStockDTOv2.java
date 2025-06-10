package com.newstoss.news.adapter.out.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLRelatedStockDTOv2 {
    @JsonProperty("news_id")
    private String newsId;
    private List<String> stocks;
}
