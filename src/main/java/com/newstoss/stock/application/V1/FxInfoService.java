package com.newstoss.stock.application.V1;

import com.newstoss.stock.adapter.inbound.dto.response.v1.FxResponseDto;
import com.newstoss.stock.application.port.in.v1.GetFxInfoUseCase;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
import com.newstoss.stock.entity.FxEncoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class FxInfoService implements GetFxInfoUseCase {

    private final FxInfoPort port;
    private final FxEncoder fxEncoder;

    @Override
    public FxResponseDto CurrentFxInfo(String code, String symbol) {
        String type;
        String fxCode;
        if (code.equals("FX")) {
            type = fxEncoder.fxConvert(symbol).get(0);
            fxCode = fxEncoder.fxConvert(symbol).get(1);
        } else if (code.equals("Feed")) {
            type = fxEncoder.feedConvert(symbol).get(0);
            fxCode = fxEncoder.feedConvert(symbol).get(1);
        } else {
            type = fxEncoder.bondsConvert(symbol).get(0);
            fxCode = fxEncoder.bondsConvert(symbol).get(1);
        }
        return port.FxInfo(type, fxCode);
    }



}
