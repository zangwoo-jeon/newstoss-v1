package com.newstoss.news.application.ml.v1.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedStockDTO;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedStockDTOv1;
import com.newstoss.news.application.ml.v1.port.in.GetRelatedStocksUseCase;
import com.newstoss.news.application.ml.v1.port.out.MLNewsPortV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class GetRelatedStocks implements GetRelatedStocksUseCase {
    private final MLNewsPortV1 mlNewsPortV1;

    //stock_name만 줄 때 코드
    @Override
    public List<RelatedStockDTO> exec(String newsId) {
        List<MLRelatedStockDTOv1> rawStocks = mlNewsPortV1.getRelatedStock(newsId);
        log.debug("관련 주식 원시 데이터: {}", rawStocks);
        return rawStocks.stream()
                .map(stock -> new RelatedStockDTO(stock.getStocks()))
                .collect(Collectors.toList());
    }

    //stock_code, name 줄 때 코드
//    @Override
//    public List<RelatedStockDTO> exec(String newsId) {
//        List<MLRelatedStockDTO> rawStocks = mlNewsPort.getRelatedStock(newsId);
//        return rawStocks.stream()
//                .flatMap(dto -> dto.getStocks().stream())
//                .map(stock -> new RelatedStockDTO(stock.getName(), stock.getCode()))
//                .collect(Collectors.toList());
//    }
}
