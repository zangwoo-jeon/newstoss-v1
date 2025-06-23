package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLExternalDTO {

    @JsonProperty("news_id")
    private String newsId;

    @JsonProperty("d_minus_5_date_close")
    private double dMinus5DateClose;

    @JsonProperty("d_minus_5_date_volume")
    private double dMinus5DateVolume;

    @JsonProperty("d_minus_5_date_foreign")
    private double dMinus5DateForeign;

    @JsonProperty("d_minus_5_date_institution")
    private double dMinus5DateInstitution;

    @JsonProperty("d_minus_5_date_individual")
    private double dMinus5DateIndividual;

    @JsonProperty("d_minus_4_date_close")
    private double dMinus4DateClose;

    @JsonProperty("d_minus_4_date_volume")
    private double dMinus4DateVolume;

    @JsonProperty("d_minus_4_date_foreign")
    private double dMinus4DateForeign;

    @JsonProperty("d_minus_4_date_institution")
    private double dMinus4DateInstitution;

    @JsonProperty("d_minus_4_date_individual")
    private double dMinus4DateIndividual;

    @JsonProperty("d_minus_3_date_close")
    private double dMinus3DateClose;

    @JsonProperty("d_minus_3_date_volume")
    private double dMinus3DateVolume;

    @JsonProperty("d_minus_3_date_foreign")
    private double dMinus3DateForeign;

    @JsonProperty("d_minus_3_date_institution")
    private double dMinus3DateInstitution;

    @JsonProperty("d_minus_3_date_individual")
    private double dMinus3DateIndividual;

    @JsonProperty("d_minus_2_date_close")
    private double dMinus2DateClose;

    @JsonProperty("d_minus_2_date_volume")
    private double dMinus2DateVolume;

    @JsonProperty("d_minus_2_date_foreign")
    private double dMinus2DateForeign;

    @JsonProperty("d_minus_2_date_institution")
    private double dMinus2DateInstitution;

    @JsonProperty("d_minus_2_date_individual")
    private double dMinus2DateIndividual;

    @JsonProperty("d_minus_1_date_close")
    private double dMinus1DateClose;

    @JsonProperty("d_minus_1_date_volume")
    private double dMinus1DateVolume;

    @JsonProperty("d_minus_1_date_foreign")
    private double dMinus1DateForeign;

    @JsonProperty("d_minus_1_date_institution")
    private double dMinus1DateInstitution;

    @JsonProperty("d_minus_1_date_individual")
    private double dMinus1DateIndividual;

    @JsonProperty("d_plus_1_date_close")
    private double dPlus1DateClose;

    @JsonProperty("d_plus_2_date_close")
    private double dPlus2DateClose;

    @JsonProperty("d_plus_3_date_close")
    private double dPlus3DateClose;

    @JsonProperty("d_plus_4_date_close")
    private double dPlus4DateClose;

    @JsonProperty("d_plus_5_date_close")
    private double dPlus5DateClose;

    @JsonProperty("fx")
    private double fx;

    @JsonProperty("bond10y")
    private double bond10y;

    @JsonProperty("base_rate")
    private double baseRate;
}
