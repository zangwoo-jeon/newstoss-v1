package com.newstoss.stock.adapter.inbound.api.v1;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.dto.response.FxResponseDto;
import com.newstoss.stock.application.port.in.GetFxInfoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "FX, 원자재, 채권 조회",
            description = "FX, 원자재, 채권 조회합니다. type 에 종류를 적고, symbol에 조회할 종목이름을 적습니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카테고리 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FxResponseDto.class)
                            )
                    )
            }
    )
    @Parameters({
            @Parameter(name = "type", description = "조회할 주식 타입", example = "FX , Feed, Bonds"),
            @Parameter(name = "symbol", description = "조회할 코드 이름", example = "FX: dollar, yen, DOW, NQ, SP500, Nikkei, Hangsang"
            + ", ShangHai , Feed: GOLD, SILVER, WTI, CORN, COFFEE ,COTTON , Bonds: KRBONDS 1,3,5,10,20,30 USBONDS 1,10,30")
    })
    @GetMapping("/FX")
    public ResponseEntity<?> getFX(@RequestParam String type,
                                   @RequestParam String symbol) {
        FxResponseDto fxResponseDto = getFxInfoUseCase.CurrentFxInfo(type, symbol);
        return ResponseEntity.ok(new SuccessResponse<>(true,"외환 지수 조회에 성공하였습니다.", fxResponseDto));
    }
}
