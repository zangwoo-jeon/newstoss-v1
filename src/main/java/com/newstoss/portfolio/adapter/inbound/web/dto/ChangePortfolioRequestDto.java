package com.newstoss.portfolio.adapter.inbound.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePortfolioRequestDto {
    private boolean isAdd;
    private Integer stockCount;
    private Integer price;
}
