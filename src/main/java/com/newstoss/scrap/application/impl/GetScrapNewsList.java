package com.newstoss.scrap.application.impl;

import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;
import com.newstoss.news.application.impl.ml.v2.NewsDTOv2Mapper;
import com.newstoss.news.application.port.out.ml.MLNewsRelatedPortV2;
import com.newstoss.scrap.application.port.in.GetScrapNewsListUseCase;
import com.newstoss.scrap.application.port.out.ScrapNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetScrapNewsList implements GetScrapNewsListUseCase {
    private final ScrapNewsPort scrapNewsPort;
    private final MLNewsRelatedPortV2 mlPort;

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
