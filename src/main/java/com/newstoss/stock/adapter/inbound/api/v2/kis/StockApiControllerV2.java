package com.newstoss.stock.adapter.inbound.api.v2.kis;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.dto.response.v1.CategoryPageResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.v2.CategoryPageDto;
import com.newstoss.stock.application.port.in.v1.GetCategoryUseCase;
import com.newstoss.stock.application.port.in.v1.GetStockPeriodUseCase;
import com.newstoss.stock.application.port.in.v2.GetCategorySimpleUseCase;
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

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/stocks")
@Tag(name = "주식 조회 API V2" , description = "주식 조회 API V2")
public class StockApiControllerV2 {

    private final GetStockPeriodUseCase stockPeriodUseCase;
    private final GetCategorySimpleUseCase categorySimpleUseCase;

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
        CategoryPageDto categoryPageResponseDto = categorySimpleUseCase.getCategorySimple(categoryName, page-1);
        return ResponseEntity.ok(new SuccessResponse<>(true, "카테고리별 종목 조회 성공", categoryPageResponseDto));
    }

}
