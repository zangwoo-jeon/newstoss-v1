package com.newstoss.news.application.news.v1.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedReportDTO;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedReportDTOv1;
import com.newstoss.news.application.news.v1.port.in.GetRelatedReportUseCase;
import com.newstoss.news.application.news.v1.port.out.MLNewsPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetRelatedReport implements GetRelatedReportUseCase {
    private final MLNewsPortV1 mlNewsPortV1;

    @Override
    public List<RelatedReportDTO> exec(String newsId) {
        List<MLRelatedReportDTOv1> rawReport = mlNewsPortV1.getRelatedReport(newsId);

        return rawReport.stream().map(news -> new RelatedReportDTO(
                news.getStockName(),
                news.getTitle(),
                news.getSecFirm(),
                news.getDate(),
                news.getViewCount(),
                news.getUrl(),
                news.getTargetPrice(),
                news.getOpinion(),
                news.getReportContent(),
                news.getEmbedding(),
                news.getSimilarity()))
                .toList();
    }
}
