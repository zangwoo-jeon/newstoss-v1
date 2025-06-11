package com.newstoss.stock.adapter.inbound.api.v1;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.dto.response.FxResponseDto;
import com.newstoss.stock.application.port.in.GetFxInfoUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
@Tag(name = "주식 외환, 지수 API", description = "주식 페이지에서 사이드바의 외환과 지수")
public class StockSideBarController {

    private final GetFxInfoUseCase getFxInfoUseCase;

    @GetMapping("/FX")
    public ResponseEntity<?> getFX(@RequestParam String type,
                                   @RequestParam String symbol) {
        FxResponseDto fxResponseDto = getFxInfoUseCase.CurrentFxInfo(type, symbol);
        return ResponseEntity.ok(new SuccessResponse<>(true,"외환 지수 조회에 성공하였습니다.", fxResponseDto));
    }
}
