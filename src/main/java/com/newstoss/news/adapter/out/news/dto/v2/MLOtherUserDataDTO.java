package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLOtherUserDataDTO {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_pnl")
    private String userPnl;
    private String asset;
    @JsonProperty("invest_score")
    private String investScore;
    @JsonProperty("member_stocks")
    private List<MLRelatedStockDTOv2> memberStocks;


}
