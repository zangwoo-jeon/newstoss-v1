package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.v2.*;
import com.newstoss.news.application.port.in.ml.v2.*;
import com.newstoss.news.application.port.out.redis.HighlightNewsCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceV2 {

    private final GetRealTimeNewsUseCaseV2 getRealTimeNews;
    private final GetNewsDetailUseCaseV2 getDetailNews;
    private final GetRelatedNewsUseCaseV2 getRelatedNews;
    private final GetAllNewsUseCaseV2 getAllNews;
    private final GetHighlightNewsUseCase getHighlight;
    private final HighlightNewsCachePort highlightNews;
    private final GetNewsMataDataUseCaseV2 mataDataUseCaseV2;

    public List<NewsDTOv2> getRealTimeNews(){
        return getRealTimeNews.exec();
    }

    public NewsDTOv2 getDetailNews(String newsId){
        return getDetailNews.exec(newsId);
    }

    public List<RelatedNewsDTOv2> getRelatedNews(String newsId) {
        return getRelatedNews.exec(newsId);
    }

    public List<NewsDTOv2> getAllNews(GetAllNewsDTO getAllNewsDTO) { return getAllNews.exec(getAllNewsDTO); }

    public List<NewsDTOv2> getHighlightNews() {
        return getHighlight.exec();
    }

    public List<NewsMathRelatedDTO> highlightWithRedis(){return highlightNews.loadHighlightsWithRelated();}

    public NewsMetaDataDTO getNewsMeta(String newsId){
        return mataDataUseCaseV2.exec(newsId);
    };
}
