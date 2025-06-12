package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.inbound.dto.response.FxResponseDto;

public interface GetFxInfoUseCase {
    FxResponseDto CurrentFxInfo(String code, String symbol);
}
