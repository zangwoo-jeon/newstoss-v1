package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.inbound.dto.response.IndicesResponseDto;

public interface GetIndiceUseCase {
    IndicesResponseDto getIndiceInfo(String market, String startDate, String endDate);
}
