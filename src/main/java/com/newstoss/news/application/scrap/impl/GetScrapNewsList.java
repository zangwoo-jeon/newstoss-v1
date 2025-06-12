package com.newstoss.news.application.scrap.impl;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.application.ml.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.ml.v2.port.out.MLNewsPortV2;
import com.newstoss.news.application.scrap.port.in.GetScrapNewsListUseCase;
import com.newstoss.news.application.scrap.port.out.ScrapNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetScrapNewsList implements GetScrapNewsListUseCase {
    private final ScrapNewsPort scrapNewsPort;
    private final MLNewsPortV2 mlPort;

    @Override
    public List<NewsDTOv2> exec(UUID memberId) {
        List<String> newsIds = scrapNewsPort.getAll(memberId);
        if (newsIds.isEmpty()) return List.of();
//        parallelStream() 병렬처리 시 사용 고려 중
        return newsIds.stream()
                .map(mlPort::getDetailNews)
                .filter(Objects::nonNull)
                .map(NewsDTOv2Mapper::from)
                .toList();
    }
}
