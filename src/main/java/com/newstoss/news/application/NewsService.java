package com.newstoss.news.application;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.adapter.in.web.dto.RelatedNewsDTO;
import com.newstoss.news.adapter.in.web.dto.RelatedStockDTO;
import com.newstoss.news.application.port.in.GetNewsDetailUseCase;
import com.newstoss.news.application.port.in.GetRealTimeNewsUseCase;
import com.newstoss.news.application.port.in.GetRelatedNewsUseCase;
import com.newstoss.news.application.port.in.GetRelatedStocksUseCase;
import com.newstoss.news.application.service.GetDetailNews;
import com.newstoss.news.application.service.GetRealTimeNews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final GetRealTimeNewsUseCase getRealTimeNews;
    private final GetNewsDetailUseCase getDetailNews;
    private final GetRelatedNewsUseCase getRelatedNews;
    private final GetRelatedStocksUseCase getRelatedStocks;

    public List<NewsDTO> getRealTimeNews(){
        return getRealTimeNews.exec();
    }

    public NewsDTO getDetailNews(String newsId){
        return getDetailNews.exec(newsId);
    }

    public List<RelatedNewsDTO> getRelatedNews(String newsId) {
        return getRelatedNews.exec(newsId);
    }

    public List<RelatedStockDTO> getRelatedStock(String newsId){
        return getRelatedStocks.exec(newsId);
    }
}
