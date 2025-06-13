package com.newstoss.news.application.news.v1.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedStockDTO;

import java.util.List;

public interface GetRelatedStocksUseCase {
    List<RelatedStockDTO> exec(String newsId);
}
