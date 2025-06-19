package com.newstoss.stock.application.stream;

import com.newstoss.global.kis.dto.KisApiRequestDto;
import com.newstoss.stock.adapter.inbound.dto.response.v1.IndicesResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndiceInfoDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisIndicePriceDto;
import com.newstoss.stock.adapter.outbound.kis.dto.response.KisApiResponseDto;
import com.newstoss.stock.application.port.out.kis.IndicePort;
import com.newstoss.stock.application.sse.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class IndicesHandler implements KisApiMessageHandler {

    private final IndicePort indices;
    private final EmitterRepository emitterRepository;

    @Override
    public boolean supports(String type) {
        return "indices".equals(type);
    }

    @Override
    public void handle(KisApiRequestDto dto) {
        Map<String, String> FxPayLoad = dto.getPayload();
        String market = FxPayLoad.get("market");
        String startDate = FxPayLoad.get("startDate");
        String endDate = FxPayLoad.get("endDate");
        KisApiResponseDto<KisIndiceInfoDto, List<KisIndicePriceDto>> indicesInfo = indices.getIndiceInfo(market, startDate, endDate);
        KisIndiceInfoDto info = indicesInfo.getOutput1();
        List<KisIndicePriceDto> output2 = indicesInfo.getOutput2();
        IndicesResponseDto responseDto = new IndicesResponseDto(info, output2);

        Map<String, SseEmitter> allEmitters = emitterRepository.findAllEmitter();
        allEmitters.forEach((emitterId, emitter) -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("indices")
                                .data(responseDto)
                                .id(market)
                );
            } catch (Exception e) {
                log.warn("SSE 전송 실패 → emitterId: {}", emitterId, e);
                emitterRepository.deleteById(emitterId); // 실패한 emitter 정리
            }
        });
        log.info("indices 요청 성공");

    }
}
