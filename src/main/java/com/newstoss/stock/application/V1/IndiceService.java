package com.newstoss.stock.application.V1;

import com.newstoss.stock.adapter.inbound.dto.response.IndicesResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndiceInfoDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndicePriceDto;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;
import com.newstoss.stock.application.port.in.GetIndiceUseCase;
import com.newstoss.stock.application.port.out.kis.IndicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndiceService implements GetIndiceUseCase {

    private final IndicePort kisIndicePort;

    @Override
    public IndicesResponseDto getIndiceInfo(String market, String startDate, String endDate) {
        KisApiResponseDto<KisIndiceInfoDto, List<KisIndicePriceDto>> indiceInfo = kisIndicePort.getIndiceInfo(market, startDate, endDate);
        KisIndiceInfoDto info = indiceInfo.getOutput1();
        List<KisIndicePriceDto> prices = indiceInfo.getOutput2();
        return new IndicesResponseDto(info, prices);
    }
}
