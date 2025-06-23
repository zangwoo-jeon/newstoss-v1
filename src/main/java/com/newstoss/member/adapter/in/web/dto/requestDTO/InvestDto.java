package com.newstoss.member.adapter.in.web.dto.requestDTO;

import lombok.Data;

import java.util.UUID;

@Data
public class InvestDto {
    private UUID memberId;
    private Long investScore;
}
