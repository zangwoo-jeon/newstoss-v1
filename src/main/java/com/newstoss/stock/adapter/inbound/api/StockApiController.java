package com.newstoss.stock.adapter.inbound.api;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.api.dto.response.CategoriesResponseDto;
import com.newstoss.stock.adapter.inbound.api.dto.response.CategoryStockResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.in.GetCategoryUseCase;
import com.newstoss.stock.entity.Stock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
@Slf4j
public class StockApiController {
    private final GetCategoryUseCase getCategoryUseCase;

    @Operation(
            summary = "카테고리 조회",
            description = "주식 카테고리를 조회합니다. KOSPI, KOSDAQ 등 다양한 카테고리를 지원합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카테고리 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoriesResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/categories")
    public ResponseEntity<?> StockCategories() {
        List<String> categories = getCategoryUseCase.getCategories();
        List<CategoriesResponseDto> categoriesResponseDtos = categories.stream()
                .map(CategoriesResponseDto::new)
                .toList();
        return ResponseEntity.ok(new SuccessResponse<>(true, "카테고리 조회 성공", categoriesResponseDtos));
    }

    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String categoryName) {
        List<Stock> stockByCategory = getCategoryUseCase.getStockByCategory(categoryName);
        List<CategoryStockResponseDto> list = stockByCategory.stream()
                .map(stock -> {
                    CategoryStockResponseDto categoryStockResponseDto = new CategoryStockResponseDto();
                    categoryStockResponseDto.setStockName(stock.getName());
                    categoryStockResponseDto.setStockCode(stock.getStockCode());
                    return categoryStockResponseDto;
                }).toList();
        return ResponseEntity.ok(new SuccessResponse<>(true, "카테고리별 종목 조회 성공", list));
    }
}
