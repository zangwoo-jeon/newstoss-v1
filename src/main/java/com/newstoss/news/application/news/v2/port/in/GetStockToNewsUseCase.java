package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.StockNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsDTOv2;

import java.util.List;

public interface GetStockToNewsUseCase {
    List<NewsDTOv2> exec(StockNewsDTO stockNewsDTO);
}
