package com.newstoss.stock.application.port.in.v1;

import com.newstoss.stock.adapter.inbound.dto.response.v1.IndicesResponseDto;

public interface GetIndiceUseCase {
    IndicesResponseDto getIndiceInfo(String market, String startDate, String endDate);
}
