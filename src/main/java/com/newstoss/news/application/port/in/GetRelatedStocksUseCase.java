package com.newstoss.news.application.port.in;

import com.newstoss.news.adapter.in.web.dto.RelatedStockDTO;

import java.util.List;

public interface GetRelatedStocksUseCase {
    List<RelatedStockDTO> exec(String newsId);
}
