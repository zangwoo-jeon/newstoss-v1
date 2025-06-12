package com.newstoss.news.application.ml.service;

import com.newstoss.news.adapter.in.web.news.dto.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMetaDataDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.RelatedNewsDTOv2;
import com.newstoss.news.application.ml.v2.port.in.*;
import com.newstoss.news.application.redis.port.in.HighlightNewsCacheUseCase;
import com.newstoss.news.application.redis.port.out.HighlightNewsCachePort;
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
    private final HighlightNewsCacheUseCase highlightNewsCacheUseCase;

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

    public List<NewsMathRelatedDTOTest> highlightWithRedisTest(){return highlightNewsCacheUseCase.cacheHighlightWithRelatedNewsTest();}

    public NewsMetaDataDTO getNewsMeta(String newsId){
        return mataDataUseCaseV2.exec(newsId);
    };
}
