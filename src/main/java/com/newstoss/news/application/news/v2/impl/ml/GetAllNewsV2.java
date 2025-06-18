package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetAllNewsUseCaseV2;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetAllNewsV2 implements GetAllNewsUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public List<NewsDTOv2> exec(GetAllNewsDTO dto) {
        boolean isRealTime = dto == null;

        List<MLNewsDTOv2> rawNews = mlNewsPortV2.getAllNews(dto);
//                ? mlNewsPortV2.getRealTimeNews()
//                : mlNewsPortV2.getAllNews(dto);

        return rawNews.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();
    }
}