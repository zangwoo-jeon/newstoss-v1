package com.newstoss.news.application.impl.news.v2;


import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsMetaDataDTO;
import com.newstoss.news.adapter.in.web.dto.news.v2.RelatedNewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsMataDataDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLRelatedNewsDTOv2;

import java.time.LocalDateTime;

public class NewsDTOv2Mapper {
    public static NewsDTOv2 from(MLNewsDTOv2 ml) {
        return new NewsDTOv2(ml.getNewsId(),  LocalDateTime.parse(ml.getWdate()), ml.getTitle(), ml.getArticle(), ml.getUrl(), ml.getPress(), ml.getImage());
    }

    public static RelatedNewsDTOv2 from(MLRelatedNewsDTOv2 ml) {
        return new RelatedNewsDTOv2(
                ml.getNewsId(),
                LocalDateTime.parse(ml.getWdate()),
                ml.getTitle(),
                ml.getArticle(),
                ml.getUrl(),
                ml.getPress(),
                ml.getImage(),
                ml.getSimilarity()
        );
    }


    public static NewsMetaDataDTO from(MLNewsMataDataDTOv2 ml) {
        return new NewsMetaDataDTO(ml.getNewsId(), ml.getSummary(), ml.getStockList(), ml.getIndustryList(), ml.getStockViewList());
    }
}
