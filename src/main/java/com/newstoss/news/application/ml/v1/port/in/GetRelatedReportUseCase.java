package com.newstoss.news.application.ml.v1.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedReportDTO;

import java.util.List;

public interface GetRelatedReportUseCase{
    List<RelatedReportDTO> exec(String newsId);
}
