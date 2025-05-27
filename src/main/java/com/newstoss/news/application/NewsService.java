package com.newstoss.news.application;

import com.newstoss.news.adapter.in.web.dto.NewsDTO;
import com.newstoss.news.application.query.GetDetailNews;
import com.newstoss.news.application.query.GetRealTimeNews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final GetRealTimeNews getRealTimeNews;
    private final GetDetailNews getDetailNews;
    private final Get

    public List<NewsDTO> getRealTimeNews(){
        return getRealTimeNews.exec();
    }

    public NewsDTO getDetailNews(String newsId){
        return getDetailNews.exec(newsId);
    }
}
