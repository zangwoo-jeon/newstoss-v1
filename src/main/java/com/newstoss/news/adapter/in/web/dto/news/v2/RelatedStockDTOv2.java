package com.newstoss.news.adapter.in.web.dto.news.v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedStockDTOv2 {
    private List<String> stockName;
}
