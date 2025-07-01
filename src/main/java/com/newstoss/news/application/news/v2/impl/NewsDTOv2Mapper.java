package com.newstoss.news.application.news.v2.impl;


import com.newstoss.news.adapter.in.web.news.dto.v2.*;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.IndustryListDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.NewsMetaDataDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedNewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedStockDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.*;

import java.time.LocalDateTime;
import java.util.List;

public class NewsDTOv2Mapper {
    public static NewsDTOv2 from(MLNewsDTOv2 ml) {
        return new NewsDTOv2(ml.getNewsId(),  LocalDateTime.parse(ml.getWdate()), ml.getTitle(), ml.getArticle(), ml.getUrl(), ml.getPress(), ml.getImage(), mapToRelatedStockDTOs(ml.getStock()), ml.getImpactScore() );
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
                ml.getSimilarity(),
                mapToRelatedStockDTOs(ml.getStock())
        );
    }


    public static NewsMetaDataDTO from(MLNewsMataDataDTOv2 ml) {
        return new NewsMetaDataDTO(ml.getNewsId(), ml.getSummary(), mapToRelatedStockDTOs(ml.getStockList()),mapToRelatedStockDTOs(ml.getStockViewList()), mapToIndustryListDTOs(ml.getIndustryList()), ml.getImpact_score());
    }

    public static HighlightNewsDTO from (MLHighlightNewsDTOv2 ml){
        return new HighlightNewsDTO(ml.getNewsId(), LocalDateTime.parse(ml.getWdate()), ml.getTitle(), ml.getImage(), ml.getPress(), ml.getSummary(), ml.getImpactScore(), ml.getUrl(), mapToRelatedStockDTOs(ml.getStock()));
    }

    private static List<RelatedStockDTOv2> mapToRelatedStockDTOs(List<MLRelatedStockDTOv2> mlList) {
        if (mlList == null) return List.of();
        return mlList.stream()
                .map(ml -> new RelatedStockDTOv2(ml.getStockId(), ml.getStockName())) // 이름과 필드는 맞게 작성
                .toList();
    }

    private static List<IndustryListDTO> mapToIndustryListDTOs(List<MLIndustryListDTO> mlList) {
        if (mlList == null) return List.of();
        return mlList.stream()
                .map(ml -> new IndustryListDTO(ml.getIndustryId(),ml.getIndustryId(), ml.getIndustryName())) // 필드명에 맞춰 작성
                .toList();
    }

    public static RecommendNewsDTO mapToRecommend(MLRecommendNewsDTO ml) {
        return new RecommendNewsDTO(ml.getUserClickCount(),
                ml.isUseOtherUser(), ml.getOtherUserData(),mapRecommendNews(ml.getNewsData())); // 필드명에 맞춰 작성
    }

    private static List<RecommendNewsDateDTO> mapRecommendNews(List<MLRecommendNewsDataDTO> ml){
        return ml.stream()
                .map(news -> new RecommendNewsDateDTO(news.getNewsId(), news.getWdate(), news.getTitle(), news.getSummary(),
                        news.getImage(),news.getPress(), news.getUrl(),news.getClickScore(),news.getRecommendReasons(),mapToRelatedStockDTOs(news.getStock())))
                .toList();
    }


    public static ExternalDTO extenal(MLExternalDTO mlexternal){
        return new ExternalDTO(mlexternal.getNewsId(), mlexternal.getDMinus5DateClose(), mlexternal.getDMinus5DateVolume(),
                mlexternal.getDMinus5DateForeign(), mlexternal.getDMinus5DateInstitution(), mlexternal.getDMinus5DateIndividual(),
                mlexternal.getDMinus4DateClose(), mlexternal.getDMinus4DateVolume(), mlexternal.getDMinus4DateForeign(), mlexternal.getDMinus4DateInstitution(),
                mlexternal.getDMinus4DateIndividual(), mlexternal.getDMinus3DateClose(), mlexternal.getDMinus3DateVolume(), mlexternal.getDMinus3DateForeign(),
                mlexternal.getDMinus3DateInstitution(), mlexternal.getDMinus3DateIndividual(), mlexternal.getDMinus2DateClose(), mlexternal.getDMinus2DateVolume(),
                mlexternal.getDMinus2DateForeign(), mlexternal.getDMinus2DateInstitution(), mlexternal.getDMinus2DateIndividual(), mlexternal.getDMinus1DateClose(),
                mlexternal.getDMinus1DateVolume(), mlexternal.getDMinus1DateForeign(), mlexternal.getDMinus1DateInstitution(),
                mlexternal.getDMinus1DateIndividual(), mlexternal.getDPlus1DateClose(), mlexternal.getDPlus2DateClose(), mlexternal.getDMinus3DateClose(),
                mlexternal.getDPlus4DateClose(), mlexternal.getDPlus5DateClose(), mlexternal.getFx(), mlexternal.getBond10y(), mlexternal.getBaseRate());
    }
}
