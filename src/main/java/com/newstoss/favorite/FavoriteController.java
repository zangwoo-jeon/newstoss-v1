package com.newstoss.favorite;

import com.newstoss.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final FavoriteRepository favoriteRepository;

    @GetMapping("/{memberId}")
    public ResponseEntity<SuccessResponse<Object>> getFavoriteGroup(@PathVariable UUID memberId) {
        List<Favorite> result = favoriteService.getFavoritesByMemberId(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심종목 조회 성공", result));
    }


    @PostMapping("/{memberId}")
    public ResponseEntity<SuccessResponse<Object>> addFavoriteGroup(
            @PathVariable UUID memberId,
            @RequestBody Favorite favorite) {
        favorite.setMemberId(memberId);
        favoriteService.saveFavorite(favorite);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심종목 추가 성공", null));
    }

    @DeleteMapping("/{memberId}/{groupId}")
    public ResponseEntity<SuccessResponse<Object>> deleteFavoriteGroup(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId) {
        favoriteService.deleteFavoriteByMemberIdAndGroupId(memberId, groupId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심종목 제거 성공", null));
    }

    @PutMapping("/{memberId}/{groupId}/main")
    public ResponseEntity<SuccessResponse<Object>> setMainFavorite(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId) {
        Optional<Favorite> updatedFavoriteOptional = favoriteService.setMainFavoriteGroup(memberId, groupId);
        if (updatedFavoriteOptional.isPresent()) {
            Favorite updatedFavorite = updatedFavoriteOptional.get();
            System.out.println("Successfully set main for groupId: " + updatedFavorite.getGroupId() + ", main: " + updatedFavorite.isMain()); // isMain() 사용
            return ResponseEntity.ok(new SuccessResponse<>(true, "주요 관심종목 설정 성공", updatedFavorite)); // 변경된 객체 반환하거나 null
        } else {
            System.out.println("Favorite group not found for memberId=" + memberId + ", groupId=" + groupId + ". Cannot set main.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse<>(false, "해당 관심종목 그룹을 찾을 수 없습니다.", null));
        }
    }

}
