package com.newstoss.news.adapter.in.web.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedStockDTOv2;
import com.newstoss.news.adapter.out.news.dto.v2.MLRelatedStockDTOv2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherUserDataDTO {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_pnl")
    private String userPnl;
    private String asset;
    @JsonProperty("invest_score")
    private String investScore;
    @JsonProperty("member_stocks")
    private List<RelatedStockDTOv2> memberStocks;
}
