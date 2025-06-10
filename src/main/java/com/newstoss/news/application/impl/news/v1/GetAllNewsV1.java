package com.newstoss.news.application.impl.news.v1;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.dto.v1.MLNewsDTOv1;
import com.newstoss.news.application.port.in.ml.v1.GetAllNewsUseCase;
import com.newstoss.news.application.port.out.ml.v1.MLNewsPortV1;
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
