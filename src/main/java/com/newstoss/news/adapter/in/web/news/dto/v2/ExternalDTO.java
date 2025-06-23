package com.newstoss.news.adapter.in.web.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalDTO {

    @JsonProperty("news_id")
    private String newsId;

    @JsonProperty("dMinus5Close")
    private double dMinus5Close;

    @JsonProperty("dMinus5Volume")
    private double dMinus5Volume;

    @JsonProperty("dMinus5Foreign")
    private double dMinus5Foreign;

    @JsonProperty("dMinus5Institution")
    private double dMinus5Institution;

    @JsonProperty("dMinus5Individual")
    private double dMinus5Individual;

    @JsonProperty("dMinus4Close")
    private double dMinus4Close;

    @JsonProperty("dMinus4Volume")
    private double dMinus4Volume;

    @JsonProperty("dMinus4Foreign")
    private double dMinus4Foreign;

    @JsonProperty("dMinus4Institution")
    private double dMinus4Institution;

    @JsonProperty("dMinus4Individual")
    private double dMinus4Individual;

    @JsonProperty("dMinus3Close")
    private double dMinus3Close;

    @JsonProperty("dMinus3Volume")
    private double dMinus3Volume;

    @JsonProperty("dMinus3Foreign")
    private double dMinus3Foreign;

    @JsonProperty("dMinus3Institution")
    private double dMinus3Institution;

    @JsonProperty("dMinus3Individual")
    private double dMinus3Individual;

    @JsonProperty("dMinus2Close")
    private double dMinus2Close;

    @JsonProperty("dMinus2Volume")
    private double dMinus2Volume;

    @JsonProperty("dMinus2Foreign")
    private double dMinus2Foreign;

    @JsonProperty("dMinus2Institution")
    private double dMinus2Institution;

    @JsonProperty("dMinus2Individual")
    private double dMinus2Individual;

    @JsonProperty("dMinus1Close")
    private double dMinus1Close;

    @JsonProperty("dMinus1Volume")
    private double dMinus1Volume;

    @JsonProperty("dMinus1Foreign")
    private double dMinus1Foreign;

    @JsonProperty("dMinus1Institution")
    private double dMinus1Institution;

    @JsonProperty("dMinus1Individual")
    private double dMinus1Individual;

    @JsonProperty("dPlus1Close")
    private double dPlus1Close;

    @JsonProperty("dPlus2Close")
    private double dPlus2Close;

    @JsonProperty("dPlus3Close")
    private double dPlus3Close;

    @JsonProperty("dPlus4Close")
    private double dPlus4Close;

    @JsonProperty("dPlus5Close")
    private double dPlus5Close;

    @JsonProperty("fx")
    private double fx;

    @JsonProperty("bond10y")
    private double bond10y;

    @JsonProperty("baseRate")
    private double baseRate;
}
