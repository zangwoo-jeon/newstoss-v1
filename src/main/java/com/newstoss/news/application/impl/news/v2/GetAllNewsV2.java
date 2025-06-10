package com.newstoss.news.application.impl.news.v2;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.port.in.ml.v2.GetAllNewsUseCaseV2;
import com.newstoss.news.application.port.out.ml.v2.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetAllNewsV2 implements GetAllNewsUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public List<NewsDTOv2> exec(GetAllNewsDTO getAllNewsDTO) {
        List<MLNewsDTOv2> rawNews = mlNewsPortV2.getAllNews(getAllNewsDTO);
        return rawNews.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();
    }
}