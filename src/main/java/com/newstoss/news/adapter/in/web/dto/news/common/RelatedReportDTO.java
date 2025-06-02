package com.newstoss.news.adapter.in.web.dto.news.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedReportDTO {
    private String stockName;
    private String title;
    private String secFirm;
    private Date date;
    private Long viewCount;
    private String url;
    private String targetPrice;
    private String opinion;
    private String reportContent;
    private List<Double> embedding;
    private double similarity;

}
