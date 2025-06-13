package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetSearchNewsUseCase;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSearchNewsService implements GetSearchNewsUseCase {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public List<NewsDTOv2> exec(String searchString) {
        List<MLNewsDTOv2> mlnews = mlNewsPortV2.searchNews(searchString);
        return mlnews.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();
    }
}
