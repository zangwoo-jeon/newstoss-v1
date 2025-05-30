package com.newstoss.news.application.port.in;

import com.newstoss.news.adapter.in.web.dto.common.RelatedReportDTO;

import java.util.List;

public interface GetRelatedReportUseCase{
    List<RelatedReportDTO> exec(String newsId);
}
