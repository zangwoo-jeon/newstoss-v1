package com.newstoss.news.adapter.in.web.news.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedStockDTO {
    private List<String> stockName;
//    private String code;
}
