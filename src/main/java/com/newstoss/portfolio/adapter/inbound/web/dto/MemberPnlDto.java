package com.newstoss.portfolio.adapter.inbound.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MemberPnlDto {

    private LocalDate date;
    private Long pnl;
    private Long asset;

}
