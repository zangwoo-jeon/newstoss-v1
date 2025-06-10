package com.newstoss.news.application.port.in.ml.v2;

import com.newstoss.news.adapter.in.web.dto.news.v2.RelatedStockDTOv2;

import java.util.List;

public interface GetRelatedStockUseCaseV2 {
    List<RelatedStockDTOv2> exec(String newsId);
}
