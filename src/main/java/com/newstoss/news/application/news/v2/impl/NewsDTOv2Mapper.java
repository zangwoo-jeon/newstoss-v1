package com.newstoss.news.application.news.v2.impl;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.news.adapter.in.web.news.dto.v2.*;
import com.newstoss.news.adapter.out.news.dto.v2.*;

import java.time.LocalDateTime;
import java.util.List;

public class NewsDTOv2Mapper {
    public static NewsDTOv2 from(MLNewsDTOv2 ml) {
        return new NewsDTOv2(ml.getNewsId(),  LocalDateTime.parse(ml.getWdate()), ml.getTitle(), ml.getArticle(), ml.getUrl(), ml.getPress(), ml.getImage());
    }

    public static RelatedNewsDTOv2 from(MLRelatedNewsDTOv2 ml) {
        return new RelatedNewsDTOv2(
                ml.getNewsId(),
                LocalDateTime.parse(ml.getWdate()),
                ml.getTitle(),
                ml.getPress(),
                ml.getUrl(),
                ml.getImage(),
                ml.getSummary(),
                ml.getSimilarity()
        );
    }


    public static NewsMetaDataDTO from(MLNewsMataDataDTOv2 ml) {
        return new NewsMetaDataDTO(ml.getNewsId(), ml.getSummary(), mapToRelatedStockDTOs(ml.getStockList()),mapToRelatedStockDTOs(ml.getStockViewList()), mapToIndustryListDTOs(ml.getIndustryList()), ml.getImpact_score());
    }

    public static HighlightNewsDTO from (MLHighlightNewsDTOv2 ml){
        return new HighlightNewsDTO(ml.getNewsId(), LocalDateTime.parse(ml.getWdate()), ml.getTitle(), ml.getImage(), ml.getPress(), ml.getSummary(), ml.getImpactScore());
    }

    private static List<RelatedStockDTOv2> mapToRelatedStockDTOs(List<MLRelatedStockDTOv2> mlList) {
        if (mlList == null) return List.of();
        return mlList.stream()
                .map(ml -> new RelatedStockDTOv2(ml.getStockName(), ml.getStockId())) // 이름과 필드는 맞게 작성
                .toList();
    }

    private static List<IndustryListDTO> mapToIndustryListDTOs(List<MLIndustryListDTO> mlList) {
        if (mlList == null) return List.of();
        return mlList.stream()
                .map(ml -> new IndustryListDTO(ml.getIndustryId(),ml.getIndustryId(), ml.getIndustryName())) // 필드명에 맞춰 작성
                .toList();
    }
}
