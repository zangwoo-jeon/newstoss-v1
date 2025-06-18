package com.newstoss.favorite.FavoriteStock;

import com.newstoss.favorite.FavoriteGroup.FavoriteRepository;
import com.newstoss.favorite.FavoriteStock.dto.FavoriteStockResponseDto;
import com.newstoss.favorite.FavoriteStock.dto.UpdateStockSequenceRequest;
import com.newstoss.global.response.SuccessResponse;
import com.newstoss.stock.adapter.inbound.dto.response.SearchResponseDto;
import com.newstoss.stock.application.V1.StockQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "관심 종목 API", description = "관심 종목 관련 API")
public class FavoriteStockController {
    private final FavoriteRepository favoriteRepository;
    private final FavoriteStockService favoriteStockService;
    private final StockQueryService stockQueryService;

    @GetMapping("/{memberId}/{groupId}")
    @Operation(summary = "관심 종목 조회", description = "회원 ID와 그룹 ID로 관심 종목 목록을 조회합니다.")
    public ResponseEntity<List<FavoriteStockResponseDto>> getFavoriteStocks(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId) {
        List<FavoriteStock> favoriteStocks = favoriteStockService.getFavoritesByMemberIdAndGroupId(memberId, groupId);
        
        List<FavoriteStockResponseDto> response = favoriteStocks.stream()
                .map(favoriteStock -> {
                    // StockQueryService를 통해 실시간 주식 정보 조회
                    SearchResponseDto stockInfo = stockQueryService.searchStock(favoriteStock.getStock().getStockCode())
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("주식 정보를 찾을 수 없습니다."));
                    return new FavoriteStockResponseDto(stockInfo, favoriteStock.getStockSequence());
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{memberId}/{groupId}")
    @Operation(summary = "관심 종목 추가", description = "회원 ID와 그룹 ID로 관심 종목을 추가합니다.")
    public ResponseEntity<SuccessResponse<Object>> addFavoriteStock(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId,
            @RequestParam String stockCode
    ) {
        FavoriteStock favoriteStock = favoriteStockService.addFavoriteStock(memberId, groupId, stockCode);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심 종목 추가 성공", favoriteStock));
    }

    @DeleteMapping("/{memberId}/{groupId}/stock")
    @Operation(summary = "관심 종목 삭제", description = "회원 ID와 그룹 ID로 관심 종목을 삭제합니다.")
    public ResponseEntity<SuccessResponse<Object>> deleteFavoriteStock(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId,
            @RequestParam String stockCode
    ) {
        favoriteStockService.deleteFavoriteStock(memberId, groupId, stockCode);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심 종목 삭제 성공", null));
    }


    @PatchMapping("/{memberId}/{groupId}/stock/sequence")
    @Operation(summary = "관심 종목 순서 변경", description = "회원 ID와 그룹 ID로 관심 종목의 순서를 변경합니다.")
    public ResponseEntity<SuccessResponse<Object>> updateStockSequence(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId,
            @RequestBody UpdateStockSequenceRequest request
    ) {
        FavoriteStock favoriteStock = favoriteStockService.updateStockSequence(
                memberId, 
                groupId, 
                request.getStockCode(), 
                request.getNewSequence()
        );
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심 종목 순서 변경 성공", favoriteStock));
    }
}
