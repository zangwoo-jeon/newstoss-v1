package com.newstoss.stock.application;

import com.newstoss.stock.adapter.inbound.dto.response.FxResponseDto;
import com.newstoss.stock.application.port.in.GetFxInfoUseCase;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
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
    private final Map<String, List<String>> FxEncoder = new ConcurrentHashMap<>();
    private final Map<String, List<String>> FeedstockEncoder = new ConcurrentHashMap<>();
    private final Map<String, List<String>> BondsEncoder = new ConcurrentHashMap<>();

    @Override
    public FxResponseDto CurrentFxInfo(String code, String symbol) {
        String type;
        String fxCode;
        if (code.equals("FX")) {
            type = FxEncoder.get(symbol).get(0);
            fxCode = FxEncoder.get(symbol).get(1);
        } else if (code.equals("Feed")) {
            type = FeedstockEncoder.get(symbol).get(0);
            fxCode = FeedstockEncoder.get(symbol).get(1);
        } else {
            type = BondsEncoder.get(symbol).get(0);
            fxCode = BondsEncoder.get(symbol).get(1);
        }
        return port.FxInfo(type, fxCode);
    }

    @PostConstruct
    public void CodeInit() {
        FxEncoder.put("dollar",List.of("X","FX@KRW"));
        FxEncoder.put("yen",List.of("X","FX@KRWJS"));
        FxEncoder.put("DOW", List.of("N",".DJI"));
        FxEncoder.put("NQ", List.of("N","COMP"));
        FxEncoder.put("SP500", List.of("N","SPX"));
        FxEncoder.put("Nikkei", List.of("N","JP#NI225"));
        FxEncoder.put("HangSang", List.of("N","HK#HS"));
        FxEncoder.put("ShangHai", List.of("N","SHANG"));

        FeedstockEncoder.put("GOLD",List.of("N","NYGOLD"));
        FeedstockEncoder.put("SILVER",List.of("N","NYSILV"));
        FeedstockEncoder.put("WTI",List.of("N","WTIF"));
        FeedstockEncoder.put("CORN",List.of("N","CHICORN"));
        FeedstockEncoder.put("COFFEE",List.of("N","COFFE"));
        FeedstockEncoder.put("COTTON",List.of("N","COTTON"));

        BondsEncoder.put("KRBONDS1", List.of("I","Y0104"));
        BondsEncoder.put("KRBONDS3", List.of("I","Y0101"));
        BondsEncoder.put("KRBONDS5", List.of("I","Y0105"));
        BondsEncoder.put("KRBONDS10", List.of("I","Y0106"));
        BondsEncoder.put("KRBONDS20", List.of("I","Y0116"));
        BondsEncoder.put("KRBONDS30", List.of("I","Y0117"));
        BondsEncoder.put("USBONDS1", List.of("I","Y0203"));
        BondsEncoder.put("USBONDS10", List.of("I","Y0202"));
        BondsEncoder.put("USBONDS30", List.of("I","Y0201"));
    }

}
