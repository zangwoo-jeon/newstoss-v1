package com.newstoss.news.adapter.in.web.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.news.adapter.out.news.dto.v2.MLIndustryListDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLRelatedStockDTOv2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsMetaDataDTO {
    private String newsId;
    private String summary;
    private List<RelatedStockDTOv2> stockList;
    private List<String> stockListView;
    private List<IndustryListDTO> industryList;
    private double impactScore;
}
