package com.newstoss.stock.adapter.inbound.api.v2.kis;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.dto.response.CategoryPageResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.application.port.in.GetCategoryUseCase;
import com.newstoss.stock.application.port.in.GetStockInfoUseCase;
import com.newstoss.stock.application.port.in.GetStockPeriodUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v2/stocks")
@Tag(name = "주식 조회 API V2" , description = "주식 조회 API V2")
public class StockApiControllerV2 {

    private final GetStockInfoUseCase stockInfoUseCase;
    private final GetStockPeriodUseCase stockPeriodUseCase;
    private final GetCategoryUseCase categoryUseCase;

    public StockApiControllerV2(
            @Qualifier("StockInfoServiceV2") GetStockInfoUseCase getStockInfoUseCase,
            GetStockPeriodUseCase getStockPeriodUseCase,
            @Qualifier("StockServiceV2") GetCategoryUseCase getCategoryUseCase) {
        stockInfoUseCase = getStockInfoUseCase;
        stockPeriodUseCase = getStockPeriodUseCase;
        categoryUseCase = getCategoryUseCase;
    }

    @Operation(summary = "주식 가격 조회",
            description = "특정 주식의 현재 가격 또는 기간별 가격을 조회합니다. " +
                    "기간을 지정하지 않으면 현재 가격을 반환하며, 기간을 지정하면 해당 기간의 주식 가격을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주식 가격 조회 성공",
                            content = @Content(schema = @Schema(implementation = KisPeriodStockDto.class)))
            })

    @GetMapping("/{stockCode}")
    public ResponseEntity<?> StockPriceV2(@PathVariable String stockCode,
                                        @RequestParam(required = false) String period) {
        if (period == null || period.isEmpty()) {
            String price = stockInfoUseCase.getStockPrice(stockCode);
            return ResponseEntity.ok(new SuccessResponse<>(true, "주식 가격 조회 성공", price));
        } else {
            List<KisPeriodStockDto> stockInfoByPeriod = stockPeriodUseCase.getStockInfoByPeriod(stockCode, period);
            return ResponseEntity.ok(new SuccessResponse<>(true, "주식 기간별 가격 조회 성공", stockInfoByPeriod));
        }

    }

    @Operation(
            summary = "카테고리별 종목 조회",
            description = "특정 카테고리에 속하는 주식 종목을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카테고리별 종목 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryPageResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String categoryName,
                                               @RequestParam(defaultValue = "1") int page
    ) {
        log.info("getCategoryByName called with categoryName: {} , {}", categoryName, page);
        CategoryPageResponseDto categoryPageResponseDto = categoryUseCase.getStockByCategory(categoryName, page-1);
        return ResponseEntity.ok(new SuccessResponse<>(true, "카테고리별 종목 조회 성공", categoryPageResponseDto));
    }

}
