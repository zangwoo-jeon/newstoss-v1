package com.newstoss.news.application.port.out.ml.v2;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsMataDataDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLRelatedNewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLRelatedStockDTOv2;

import java.util.List;

public interface MLNewsPortV2 {
    List<MLNewsDTOv2> getRealTimeNews();

    MLNewsDTOv2 getDetailNews(String newsId);

    List<MLNewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO);

    List<MLRelatedNewsDTOv2> getSimilarNews(String newsId);
    
    List<MLNewsDTOv2> getHighLightNews();

    MLNewsMataDataDTOv2 getNewsMeta(String newsId);
}