package com.newstoss.news.application.impl.news;

import com.newstoss.news.adapter.in.web.dto.news.common.RelatedReportDTO;
import com.newstoss.news.adapter.out.dto.MLRelatedReportDTO;
import com.newstoss.news.application.port.in.ml.GetRelatedReportUseCase;
import com.newstoss.news.application.port.out.ml.MLNewsRelatedPortV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetRelatedReport implements GetRelatedReportUseCase {
    private final MLNewsRelatedPortV1 mlNewsRelatedPortV1;

    @Override
    public List<RelatedReportDTO> exec(String newsId) {
        List<MLRelatedReportDTO> rawReport = mlNewsRelatedPortV1.getRelatedReport(newsId);

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
