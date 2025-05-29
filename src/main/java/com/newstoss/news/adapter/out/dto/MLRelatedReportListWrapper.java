package com.newstoss.news.adapter.out.dto;

import lombok.Data;

import java.util.List;

@Data
public class MLRelatedReportListWrapper {
    private List<MLRelatedReportDTO> results;
}