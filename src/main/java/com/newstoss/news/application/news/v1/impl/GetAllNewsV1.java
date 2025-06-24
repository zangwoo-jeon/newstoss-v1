package com.newstoss.news.application.news.v1.impl;

import com.newstoss.news.adapter.in.web.news.dto.v2.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.news.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.news.v1.port.in.GetAllNewsUseCase;
import com.newstoss.news.application.news.v1.port.out.MLNewsPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetAllNewsV1 implements GetAllNewsUseCase {
    private final MLNewsPortV1 mlNewsPortV1;

    @Override
    public List<NewsDTOv1> exec(GetAllNewsDTO getAllNewsDTO) {
        List<MLNewsDTOv1> rawNews = mlNewsPortV1.getAllNews(getAllNewsDTO);
        return rawNews.stream()
                .map(NewsDTOv1Mapper::from)
                .toList();
    }
}
