package com.newstoss.news.application.news.v2.port.out;

import com.newstoss.news.adapter.in.web.news.dto.v2.GetAllNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v2.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MLNewsPortV2 {
//    List<MLNewsDTOv2> getRealTimeNews();

    MLNewsDTOv2 getDetailNews(String newsId);

    List<MLNewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO);

    List<MLRelatedNewsDTOv2> getSimilarNews(String newsId);
    
    List<MLHighlightNewsDTOv2> getHighLightNews(LocalDateTime now, LocalDateTime before);

    MLNewsMataDataDTOv2 getNewsMeta(String newsId);

    List<MLNewsDTOv2> searchNews(String searchNews);

    void chat(String clientId, String question);

    List<MLNewsDTOv2> stockToNews(int skip, int limit, String stock_code);

    List<MLRecommendNewsDTO> recommendNews(String memberId);

    MLExternalDTO external(String newsId);
}