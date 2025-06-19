package com.newstoss.stock.application.port.out.kis;


import com.newstoss.stock.adapter.inbound.dto.response.v1.FxResponseDto;

public interface FxInfoPort {
    FxResponseDto FxInfo(String code,String symbol);
}
