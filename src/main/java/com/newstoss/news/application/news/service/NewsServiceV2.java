package com.newstoss.news.application.news.service;

import com.newstoss.news.adapter.in.web.news.dto.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.*;
import com.newstoss.news.application.news.v2.port.in.*;
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
    private final GetNewsMataDataUseCaseV2 mataDataUseCaseV2;
    private final GetSearchNewsUseCase getSearchNews;
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

    public List<HighlightNewsDTO> getHighlightNews() { return getHighlight.exec(); }

    public
    List<NewsMathRelatedDTO<HighlightNewsDTO>> highlightWithRedis(){return highlightNewsCacheUseCase.loadRedis();}

    public NewsMetaDataDTO getNewsMeta(String newsId){
        return mataDataUseCaseV2.exec(newsId);
    };

    public List<NewsDTOv2> searchNews(String search) { return getSearchNews.exec(search); }
}
