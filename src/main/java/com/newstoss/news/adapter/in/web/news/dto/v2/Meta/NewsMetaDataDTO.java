package com.newstoss.news.adapter.in.web.news.dto.v2.Meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class    NewsMetaDataDTO {
    private String newsId;
    private String summary;
    private List<RelatedStockDTOv2> stockList;
    private List<RelatedStockDTOv2> stockListView;
    private List<IndustryListDTO> industryList;
    private double impactScore;
}
