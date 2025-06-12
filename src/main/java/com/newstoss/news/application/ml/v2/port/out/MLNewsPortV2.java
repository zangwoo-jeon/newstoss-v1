package com.newstoss.news.application.ml.v2.port.out;

import com.newstoss.news.adapter.in.web.news.dto.common.GetAllNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsMataDataDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLRelatedNewsDTOv2;

import java.util.List;

public interface MLNewsPortV2 {
    List<MLNewsDTOv2> getRealTimeNews();

    MLNewsDTOv2 getDetailNews(String newsId);

    List<MLNewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO);

    List<MLRelatedNewsDTOv2> getSimilarNews(String newsId);
    
    List<MLNewsDTOv2> getHighLightNews();

    MLNewsMataDataDTOv2 getNewsMeta(String newsId);
}