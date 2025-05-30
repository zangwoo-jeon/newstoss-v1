package com.newstoss.news.application;

import com.newstoss.news.adapter.in.web.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.in.web.dto.common.RelatedNewsDTO;
import com.newstoss.news.adapter.in.web.dto.common.RelatedReportDTO;
import com.newstoss.news.adapter.in.web.dto.common.RelatedStockDTO;
import com.newstoss.news.application.port.in.*;
import com.newstoss.news.application.port.in.v1.GetNewsDetailUseCaseV1;
import com.newstoss.news.application.port.in.v1.GetRealTimeNewsUseCaseV1;
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
