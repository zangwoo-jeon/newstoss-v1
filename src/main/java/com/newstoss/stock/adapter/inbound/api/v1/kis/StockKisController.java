package com.newstoss.stock.adapter.inbound.api.v1.kis;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.dto.response.IndicesResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.*;
import com.newstoss.stock.application.port.in.GetIndiceUseCase;
import com.newstoss.stock.application.port.in.GetPopularStockUseCase;
import com.newstoss.stock.application.port.in.GetStockInfoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
@Slf4j
@Tag(name = "주식 API", description = "주식 관련 API")
public class StockKisController {
    private final GetStockInfoUseCase stockInfoUseCase;
    private final GetPopularStockUseCase getPopularStockUseCase;
    private final GetIndiceUseCase indiceUseCase;
    // 주요 지수 일자별 조회
    @Operation(summary = "주요 지수 일자별 조회",
            description = "특정 시장의 주요 지수를 일자별로 조회합니다. KOSPI와 KOSDAQ 시장을 지원합니다. " +
                    "시작 날짜와 종료 날짜는 String 형식으로 입력해야 합니다.ex) 20250220",
            responses = {
            @ApiResponse(responseCode = "200", description = "지수 조회 성공",
                    content = @Content(schema = @Schema(implementation = IndicesResponseDto.class)))
            })
    @GetMapping("/indices/{market}")
    public ResponseEntity<?> getIndicesByMarket(@PathVariable String market,
                                                @RequestParam String startDate,
                                                @RequestParam String endDate) {
        log.info("getIndicesByMarket called with market: {}, startDate: {}, endDate: {}", market, startDate, endDate);
        IndicesResponseDto responseDto = indiceUseCase.getIndiceInfo(market, startDate, endDate);
        return ResponseEntity.ok(new SuccessResponse<>(true, "지수 조회 성공", responseDto ));
    }

    @Operation(summary = "상위 6개 인기 종목 조회",
            description = "인기 종목을 조회합니다. 인기 종목은 KIS API를 통해 제공됩니다.",
            responses = {
            @ApiResponse(responseCode = "200", description = "인기 종목 조회 성공",
                    content = @Content(schema = @Schema(implementation = KisPopularDto.class)))
            })
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularStocks() {
        List<KisPopularDto> top6 = getPopularStockUseCase.getPopularStock();
        return ResponseEntity.ok(new SuccessResponse<>(true, "상위 6개 인기종목을 조회하는데 성공하였습니다.", top6));
    }

    @Operation(summary = "주식 가격 조회",
            description = "특정 주식의 현재 가격 또는 기간별 가격을 조회합니다. " +
                    "기간을 지정하지 않으면 현재 가격을 반환하며, 기간을 지정하면 해당 기간의 주식 가격을 반환합니다.",
            responses = {
            @ApiResponse(responseCode = "200", description = "주식 가격 조회 성공",
                    content = @Content(schema = @Schema(implementation = KisPeriodStockDto.class)))
            })

    @GetMapping("/{stockCode}")
    public ResponseEntity<?> StockPrice(@PathVariable String stockCode,
                                           @RequestParam(required = false) String period) {
        if (period == null || period.isEmpty()) {
            String price = stockInfoUseCase.GetStockPrice(stockCode);
            return ResponseEntity.ok(new SuccessResponse<>(true, "주식 가격 조회 성공", price));
        } else if (period.equals("D")) {
            List<KisPeriodStockDto> kisPeriodStockDtos = stockInfoUseCase.GetDailyStockByPeriod(stockCode);
            return ResponseEntity.ok(new SuccessResponse<>(true, "주식 일간 가격 조회 성공", kisPeriodStockDtos));
        } else if (period.equals("M")) {
            List<KisPeriodStockDto> kisPeriodStockDtos = stockInfoUseCase.GetMonthlyStockByPeriod(stockCode);
            return ResponseEntity.ok(new SuccessResponse<>(true, "주식 가격 조회 성공", kisPeriodStockDtos));
        } else {
            List<KisPeriodStockDto> kisPeriodStockDtos = stockInfoUseCase.GetYearlyStockByPeriod(stockCode);
            return ResponseEntity.ok(new SuccessResponse<>(true, "주식 가격 조회 성공", kisPeriodStockDtos));
        }

    }

}
