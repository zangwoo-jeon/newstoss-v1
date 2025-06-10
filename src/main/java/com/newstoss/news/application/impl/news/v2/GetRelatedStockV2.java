package com.newstoss.news.application.impl.news.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.RelatedStockDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLRelatedStockDTOv2;
import com.newstoss.news.application.port.in.ml.v2.GetRelatedStockUseCaseV2;
import com.newstoss.news.application.port.out.ml.v2.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRelatedStockV2 implements GetRelatedStockUseCaseV2{
    private final MLNewsPortV2 mlNewsPortV2;

    @Override
    public List<RelatedStockDTOv2> exec(String newsId) {
        List<MLRelatedStockDTOv2> stocks = mlNewsPortV2.getRelatedStock(newsId);
        return  stocks.stream().map(NewsDTOv2Mapper::from).toList();
    }
}
