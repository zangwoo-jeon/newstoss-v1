package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.StockNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;
import com.newstoss.news.application.news.v2.impl.NewsDTOv2Mapper;
import com.newstoss.news.application.news.v2.port.in.GetStockToNewsUseCase;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class GetStockToNewsV2 implements GetStockToNewsUseCase {
    private final MLNewsPortV2 mlNewsPortV2;
    @Override
    public List<NewsDTOv2> exec(StockNewsDTO stockNewsDTO) {
        Integer skip = stockNewsDTO.getSkip();
        Integer limit = stockNewsDTO.getLimit();
        String stock = stockNewsDTO.getStockCode();
        List<MLNewsDTOv2> news = mlNewsPortV2.stockToNews(skip, limit, stock);
        return news.stream()
                .map(NewsDTOv2Mapper::from)
                .toList();
    }
}
