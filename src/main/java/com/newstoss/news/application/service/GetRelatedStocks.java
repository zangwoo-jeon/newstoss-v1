package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.common.RelatedStockDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedStockDTO;
import com.newstoss.news.application.port.in.GetRelatedStocksUseCase;
import com.newstoss.news.application.port.out.MLNewsRelatedPortV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class GetRelatedStocks implements GetRelatedStocksUseCase {
    private final MLNewsRelatedPortV1 mlNewsRelatedPortV1;

    //stock_name만 줄 때 코드
    @Override
    public List<RelatedStockDTO> exec(String newsId) {
        List<MLRelatedStockDTO> rawStocks = mlNewsRelatedPortV1.getRelatedStock(newsId);
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
