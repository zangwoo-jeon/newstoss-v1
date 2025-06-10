package com.newstoss.news.application.impl.news.v1;

import com.newstoss.news.adapter.in.web.dto.news.v1.RelatedReportDTO;
import com.newstoss.news.adapter.out.dto.v1.MLRelatedReportDTOv1;
import com.newstoss.news.application.port.in.ml.v1.GetRelatedReportUseCase;
import com.newstoss.news.application.port.out.ml.v1.MLNewsPortV1;
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
