package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.news.v2.RelatedNewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.news.v2.RelatedStockDTOv2;
import com.newstoss.news.application.port.in.ml.v2.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceV2 {

    private final GetRealTimeNewsUseCaseV2 getRealTimeNews;
    private final GetNewsDetailUseCaseV2 getDetailNews;
    private final GetRelatedNewsUseCaseV2 getRelatedNews;
    private final GetRelatedStockUseCaseV2 getRelatedStocks;
    private final GetAllNewsUseCaseV2 getAllNews;
    private final GetHighlightNewsUseCase getHighlight;

    public List<NewsDTOv2> getRealTimeNews(){
        return getRealTimeNews.exec();
    }

    public NewsDTOv2 getDetailNews(String newsId){
        return getDetailNews.exec(newsId);
    }

    public List<RelatedNewsDTOv2> getRelatedNews(String newsId) {
        return getRelatedNews.exec(newsId);
    }

    public List<RelatedStockDTOv2> getRelatedStock(String newsId){
        return getRelatedStocks.exec(newsId);
    }

    public List<NewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO) { return getAllNews.exec(getAllNewsDTO); }

    public List<NewsDTOv2> getHighlightNews() {
        return getHighlight.exec();
    }
}
