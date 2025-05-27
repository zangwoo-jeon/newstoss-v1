package com.newstoss.stock.application.port.in;

import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndicePrevDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndicePriceDto;

import java.util.List;

public interface GetIndiceUseCase {
    KisApiResponseDto<KisIndicePrevDto, List<KisIndicePriceDto>> getIndiceInfo(String market, String startDate, String endDate);
}
