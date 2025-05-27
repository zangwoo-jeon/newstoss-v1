package com.newstoss.news.application.port.out;

import com.newstoss.news.adapter.out.dto.MLNewsDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedNewsDTO;

import java.util.List;

public interface MLNewsPort {
    List<MLNewsDTO> getRealTimeNews();
    MLNewsDTO getDetailNews(String newsId);
    List<MLRelatedNewsDTO> getSimilarNews(String newsId);
}
