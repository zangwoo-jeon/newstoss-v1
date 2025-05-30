package com.newstoss.stock.adapter.inbound.api.v1;

import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.dto.request.StockCountRequestDto;
import com.newstoss.stock.adapter.inbound.dto.response.CategoriesResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.CategoryPageResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.CategoryStockResponseDto;
import com.newstoss.stock.adapter.inbound.dto.response.SearchResponseDto;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.application.port.in.GetCategoryUseCase;
import com.newstoss.stock.application.port.in.GetStockInfoUseCase;
import com.newstoss.stock.application.port.in.SearchStockUseCase;
import com.newstoss.stock.application.port.in.UpdateStockSearchCount;
import com.newstoss.stock.entity.Stock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
@Slf4j
@Tag(name = "주식 API", description = "주식 관련 API")
public class StockApiController {
    private final GetCategoryUseCase getCategoryUseCase;
    private final SearchStockUseCase searchStockUseCase;
    private final GetStockInfoUseCase getStockInfoUseCase;
    private final UpdateStockSearchCount updateStockSearchCount;
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
        Page<Stock> stockByCategory = getCategoryUseCase.getStockByCategory(categoryName, page-1);
        List<CategoryStockResponseDto> categoryStockResponseDtos = stockByCategory.stream()
                .map(stock -> {
                    CategoryStockResponseDto categoryStockResponseDto = new CategoryStockResponseDto();
                    KisStockDto stockInfo = getStockInfoUseCase.getStockInfo(stock.getStockCode());
                    categoryStockResponseDto.setStockName(stock.getName());
                    categoryStockResponseDto.setStockCode(stock.getStockCode());
                    categoryStockResponseDto.setCurrentPrice(stockInfo.getPrice());
                    categoryStockResponseDto.setChangeRate(stockInfo.getChangeRate());
                    categoryStockResponseDto.setSign(stockInfo.getSign());
                    categoryStockResponseDto.setChangeAmount(stockInfo.getChangeAmount());
                    return categoryStockResponseDto;
                }).toList();
        CategoryPageResponseDto categoryPageResponseDto = new CategoryPageResponseDto(stockByCategory.getTotalPages(), categoryStockResponseDtos);
        return ResponseEntity.ok(new SuccessResponse<>(true, "카테고리별 종목 조회 성공", categoryPageResponseDto));
    }

    @Operation(
            summary = "주식 검색",
            description = "주식 종목을 검색합니다. 키워드가 없으면 인기 검색 종목을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주식 검색 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SearchResponseDto.class)
                            )
                    )
            }
    )

    @GetMapping("/search")
    public ResponseEntity<?> searchStocks(@RequestParam(required = false) String keyword) {
        log.info("searchStocks called with keyword: {}", keyword);
        if (keyword == null || keyword.isEmpty()) {
            log.info("searchStocks called without keyword");
            List<Stock> stocks = searchStockUseCase.searchPopular();
            List<SearchResponseDto> searchResponseDtos = stocks.stream()
                    .map(stock -> {
                        KisStockDto stockInfo = getStockInfoUseCase.getStockInfo(stock.getStockCode());
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stockInfo.getPrice(),
                                stockInfo.getSign(),
                                stockInfo.getChangeAmount(),
                                stockInfo.getChangeRate()
                            );
                        }).toList();
            return ResponseEntity.ok(new SuccessResponse<>(true, "검색 인기 순위 조회에 성공하였습니다.", searchResponseDtos));
            }
        else {
            List<Stock> stocks = searchStockUseCase.searchStock(keyword);
            if (stocks.isEmpty()) {
                return ResponseEntity.ok(new SuccessResponse<>(true, "검색 결과가 없습니다.", new ArrayList<>()));
            }
            List<SearchResponseDto> searchResponseDtos = stocks.stream()
                    .map(stock -> {
                        KisStockDto stockInfo = getStockInfoUseCase.getStockInfo(stock.getStockCode());
                        return new SearchResponseDto(
                                stock.getName(),
                                stock.getStockCode(),
                                stockInfo.getPrice(),
                                stockInfo.getSign(),
                                stockInfo.getChangeAmount(),
                                stockInfo.getChangeRate()
                            );
                        }).toList();
            return ResponseEntity.ok(new SuccessResponse<>(true, "검색 결과 조회에 성공하였습니다.", searchResponseDtos));

        }
    }

    @Operation(
            summary = "주식 검색 Count 증가",
            description = "주식 검색 Count를 +1 증가시킵니다.",

            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주식 검색 Count 증가 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/search")
    public ResponseEntity<?> StockCounter(@RequestBody StockCountRequestDto dto) {
        updateStockSearchCount.StockSearchCounter(dto.getStockCode());
        return ResponseEntity.ok(new SuccessResponse<>(true, "주식 검색 Count + 1 성공", null));
    }
}
