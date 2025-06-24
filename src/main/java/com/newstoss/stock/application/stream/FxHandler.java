package com.newstoss.stock.application.stream;

import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.adapter.inbound.dto.response.v1.FxResponseDto;
import com.newstoss.stock.application.port.out.kis.FxInfoPort;
import com.newstoss.stock.application.sse.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class FxHandler implements KisApiMessageHandler{

    private final FxInfoPort fxInfoPort;
    private final EmitterRepository emitterRepository;

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

        Map<String, SseEmitter> allEmitters = emitterRepository.findAllEmitter();
        allEmitters.forEach((emitterId, emitter) -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("fx")
                                .data(fxResponseDto)
                                .id(FxCode)
                );
            } catch (Exception e) {
                log.warn("SSE 전송 실패 → emitterId: {}", emitterId, e);
                emitterRepository.deleteById(emitterId); // 실패한 emitter 정리
            }
        });

    }

}
