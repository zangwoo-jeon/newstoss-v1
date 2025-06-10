package com.newstoss.news.application.port.in.ml.v1;

import com.newstoss.news.adapter.in.web.dto.news.v1.RelatedStockDTO;

import java.util.List;

public interface GetRelatedStocksUseCase {
    List<RelatedStockDTO> exec(String newsId);
}
