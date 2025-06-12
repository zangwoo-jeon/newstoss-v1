package com.newstoss.news.adapter.out.news.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLRelatedReportDTOv1 {
    @JsonProperty("report_id")
    private Long reportId;
    @JsonProperty("stock_name")
    private String stockName;
    private String title;
    @JsonProperty("sec_firm")
    private String secFirm;
    private Date date;
    @JsonProperty("view_count")
    private Long viewCount;
    private String url;
    @JsonProperty("target_price")
    private String targetPrice; //ML api 응답에 420,000이렇게 되어있어서 String으로 받았음
    private String opinion;
    @JsonProperty("report_content")
    private String reportContent;
    private List<Double> embedding; //ML 응답 예시에 빈 리스트로 반환되어서 무슨 값 들어올지 몰름
    private double similarity;

}
