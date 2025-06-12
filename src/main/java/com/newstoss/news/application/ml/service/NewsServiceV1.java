package com.newstoss.news.application.ml.service;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedReportDTO;
import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedStockDTO;
import com.newstoss.news.application.ml.v1.port.in.GetRelatedNewsUseCase;
import com.newstoss.news.application.ml.v1.port.in.GetRelatedReportUseCase;
import com.newstoss.news.application.ml.v1.port.in.GetRelatedStocksUseCase;
import com.newstoss.news.application.ml.v1.port.in.GetNewsDetailUseCaseV1;
import com.newstoss.news.application.ml.v1.port.in.GetRealTimeNewsUseCaseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceV1 {

    private final GetRealTimeNewsUseCaseV1 getRealTimeNews;
    private final GetNewsDetailUseCaseV1 getDetailNews;
    private final GetRelatedNewsUseCase getRelatedNews;
    private final GetRelatedStocksUseCase getRelatedStocks;
    private final GetRelatedReportUseCase getRelatedReport;

    public List<NewsDTOv1> getRealTimeNews(){
        return getRealTimeNews.exec();
    }

    public NewsDTOv1 getDetailNews(String newsId){
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
}
