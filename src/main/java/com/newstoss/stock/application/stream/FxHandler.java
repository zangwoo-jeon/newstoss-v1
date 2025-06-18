package com.newstoss.stock.application.stream;

import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.adapter.inbound.dto.response.FxResponseDto;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class FxHandler implements KisApiMessageHandler{

    private final FxInfoPort fxInfoPort;

    @Override
    public boolean supports(String type) {
        return "fx".equals(type);
    }

    @Override
    public void handle(KisApiRequestDto dto) {
        Map<String, String> FxPayLoad = dto.getPayload();
        String FxType = FxPayLoad.get("fxType");
        String FxCode = FxPayLoad.get("fxCode");
        FxResponseDto fxResponseDto = fxInfoPort.FxInfo(FxType, FxCode);
    }
}
