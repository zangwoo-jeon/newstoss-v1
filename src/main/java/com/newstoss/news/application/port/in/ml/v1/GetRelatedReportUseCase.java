package com.newstoss.news.application.port.in.ml.v1;

import com.newstoss.news.adapter.in.web.dto.news.v1.RelatedReportDTO;

import java.util.List;

public interface GetRelatedReportUseCase{
    List<RelatedReportDTO> exec(String newsId);
}
