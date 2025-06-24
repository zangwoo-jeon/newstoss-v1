package com.newstoss.stock.application.port.in.v1;

import com.newstoss.stock.adapter.inbound.dto.response.v1.FxResponseDto;

public interface GetFxInfoUseCase {
    FxResponseDto CurrentFxInfo(String code, String symbol);
}
