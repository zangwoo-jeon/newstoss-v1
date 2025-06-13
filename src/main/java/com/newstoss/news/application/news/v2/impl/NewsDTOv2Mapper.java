package com.newstoss.news.application.news.v2.impl;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.news.adapter.in.web.news.dto.v2.HighlightNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMetaDataDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.RelatedNewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLHighlightNewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsMataDataDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLRelatedNewsDTOv2;

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
        return new NewsMetaDataDTO(ml.getNewsId(), ml.getSummary(), ml.getStockList(), ml.getIndustryList(), ml.getStockViewList());
    }

    public static HighlightNewsDTO from (MLHighlightNewsDTOv2 ml){
        return new HighlightNewsDTO(ml.getNewsId(), LocalDateTime.parse(ml.getWdate()), ml.getTitle(), ml.getImage(), ml.getPress(), ml.getSummary(), ml.getImpactScore());
    }
}
