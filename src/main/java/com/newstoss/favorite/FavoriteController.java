package com.newstoss.favorite;

import com.newstoss.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "회원별 관심종목 조회", description = "특정 회원의 관심종목 목록을 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<SuccessResponse<Object>> getFavoriteGroup(@PathVariable UUID memberId) {
        List<Favorite> result = favoriteService.getFavoritesByMemberId(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심종목 조회 성공", result));
    }


    @PostMapping("/{memberId}")
    public ResponseEntity<SuccessResponse<Object>> addFavoriteGroup(@PathVariable UUID memberId, @RequestBody Favorite favorite) {
        favorite.setMemberId(memberId);
        favoriteService.saveFavorite(favorite);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심종목 추가 성공", null));
    }
}
