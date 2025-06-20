package com.newstoss.news.application.news.service;

import com.newstoss.news.adapter.in.web.news.dto.v2.*;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.NewsMetaDataDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedNewsDTOv2;
import com.newstoss.news.application.news.v2.port.in.*;
import com.newstoss.news.application.redis.port.in.HighlightNewsCacheUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceV2 {

//    private final GetRealTimeNewsUseCaseV2 getRealTimeNews;
    private final GetNewsDetailUseCaseV2 getDetailNews;
    private final GetRelatedNewsUseCaseV2 getRelatedNews;
    private final GetAllNewsUseCaseV2 getAllNews;
    private final GetHighlightNewsUseCase getHighlight;
    private final GetNewsMataDataUseCaseV2 mataDataUseCaseV2;
    private final GetSearchNewsUseCase getSearchNews;
    private final HighlightNewsCacheUseCase highlightNewsCacheUseCase;
    private final GetStockToNewsUseCase getStockToNewsUseCase;
    private final GetRecommendNewsUseCase getRecommendNewsUseCase;

//    public List<NewsDTOv2> getRealTimeNews(){
//        return getRealTimeNews.exec();
//    }

    public NewsDTOv2 getDetailNews(String newsId, UUID memberId){
        return getDetailNews.exec(newsId,memberId);
    }

    public List<RelatedNewsDTOv2> getRelatedNews(String newsId) {
        return getRelatedNews.exec(newsId);
    }

    public List<NewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO) { return getAllNews.exec(getAllNewsDTO); }

//    public List<HighlightNewsDTO> getHighlightNews() { return getHighlight.exec(); }

    public
    List<NewsMathRelatedDTO<HighlightNewsDTO>> highlightWithRedis(){return highlightNewsCacheUseCase.loadRedis();}

    public NewsMetaDataDTO getNewsMeta(String newsId){
        return mataDataUseCaseV2.exec(newsId);
    };

    public List<NewsDTOv2> searchNews(String search) { return getSearchNews.exec(search); }

    public List<NewsDTOv2> stockNews(StockNewsDTO stockNewsDTO) { return getStockToNewsUseCase.exec(stockNewsDTO); }

    public List<RecommendNewsDTO> recommedNews(UUID memberId) { return getRecommendNewsUseCase.exec(memberId); }
}
