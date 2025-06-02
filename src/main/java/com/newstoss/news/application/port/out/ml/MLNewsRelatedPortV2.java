package com.newstoss.news.application.port.out.ml;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedNewsDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedReportDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedStockDTO;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;

import java.util.List;

public interface MLNewsRelatedPortV2 {
    List<MLNewsDTOv2> getRealTimeNews();

    MLNewsDTOv2 getDetailNews(String newsId);

    List<MLNewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO);

    List<MLRelatedNewsDTO> getSimilarNews(String newsId);

    List<MLRelatedStockDTO> getRelatedStock(String newsId);

    List<MLRelatedReportDTO> getRelatedReport(String newsID);
}