package com.newstoss.news.application.news.v1.port.out;

import com.newstoss.news.adapter.in.web.news.dto.v2.GetAllNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedNewsDTOv1;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedReportDTOv1;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedStockDTOv1;
import com.newstoss.news.adapter.out.news.dto.v1.MLNewsDTOv1;

import java.util.List;

public interface MLNewsPortV1 {
    List<MLNewsDTOv1> getAllNews(GetAllNewsDTO getAllNewsDTO);
    List<MLNewsDTOv1> getRealTimeNews();
    MLNewsDTOv1  getDetailNews(String newsId);
    List<MLRelatedNewsDTOv1> getSimilarNews(String newsId);
    List<MLRelatedStockDTOv1> getRelatedStock(String newsId);
    List<MLRelatedReportDTOv1> getRelatedReport(String newsID);
}
