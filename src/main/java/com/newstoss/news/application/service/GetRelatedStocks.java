package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.RelatedStockDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedStockDTO;
import com.newstoss.news.application.port.in.GetRelatedStocksUseCase;
import com.newstoss.news.application.port.out.MLNewsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetRelatedStocks implements GetRelatedStocksUseCase {
    private final MLNewsPort mlNewsPort;

    @Override
    public List<RelatedStockDTO> exec(String newsId) {
        List<MLRelatedStockDTO> rawStocks = mlNewsPort.getRelatedStock(newsId);
        return rawStocks.stream()
                .flatMap(dto -> dto.getStocks().stream())
                .map(stock -> new RelatedStockDTO(stock.getName(), stock.getCode()))
                .collect(Collectors.toList());
    }
}
