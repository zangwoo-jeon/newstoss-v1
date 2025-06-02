package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.common.RelatedNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.common.RelatedReportDTO;
import com.newstoss.news.adapter.in.web.dto.news.common.RelatedStockDTO;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.application.port.in.ml.GetAllNewsUseCase;
import com.newstoss.news.application.port.in.ml.GetRelatedNewsUseCase;
import com.newstoss.news.application.port.in.ml.GetRelatedReportUseCase;
import com.newstoss.news.application.port.in.ml.GetRelatedStocksUseCase;
import com.newstoss.news.application.port.in.ml.v2.GetNewsDetailUseCaseV2;
import com.newstoss.news.application.port.in.ml.v2.GetRealTimeNewsUseCaseV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceV2 {

    private final GetRealTimeNewsUseCaseV2 getRealTimeNews;
    private final GetNewsDetailUseCaseV2 getDetailNews;
    private final GetRelatedNewsUseCase getRelatedNews;
    private final GetRelatedStocksUseCase getRelatedStocks;
    private final GetRelatedReportUseCase getRelatedReport;
    private final GetAllNewsUseCase getAllNews;

    public List<NewsDTOv2> getRealTimeNews(){
        return getRealTimeNews.exec();
    }

    public NewsDTOv2 getDetailNews(String newsId){
        return getDetailNews.exec(newsId);
    }

    public List<RelatedNewsDTO> getRelatedNews(String newsId) {
        return getRelatedNews.exec(newsId);
    }

    public List<RelatedStockDTO> getRelatedStock(String newsId){
        return getRelatedStocks.exec(newsId);
    }

    public List<RelatedReportDTO> getRelatedReport(String newsId){
        return getRelatedReport.exec(newsId);
    }

    public List<NewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO) {
        return getAllNews.exec(getAllNewsDTO);
    }
}
