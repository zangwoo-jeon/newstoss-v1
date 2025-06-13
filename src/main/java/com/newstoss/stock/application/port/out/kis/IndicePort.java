package com.newstoss.stock.application.port.out.kis;

import com.newstoss.stock.adapter.outbound.kis.dto.KisIndiceInfoDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndicePriceDto;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;

import java.util.List;

public interface IndicePort {
    KisApiResponseDto<KisIndiceInfoDto, List<KisIndicePriceDto>> getIndiceInfo(String market, String startDate, String endDate);

}
