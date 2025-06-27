package com.newstoss.favorite.FavoriteGroup;

import com.newstoss.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
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
//@CrossOrigin("*")
@Tag(name = "관심 그룹 API", description = "관심 그룹룹 관련 API")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final FavoriteRepository favoriteRepository;

    @GetMapping("/{memberId}")
    @Operation(summary = "관심 그룹 조회", description = "회원 ID로 관심 그룹 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<Object>> getFavoriteGroup(@PathVariable UUID memberId) {
        List<Favorite> result = favoriteService.getFavoritesByMemberId(memberId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심그룹 조회 성공", result));
    }

    //관심그룹추가
    @PostMapping("/{memberId}")
    @Operation(summary = "관심 그룹 추가", description = "회원 ID로 관심 그룹을 추가합니다.")
    public ResponseEntity<SuccessResponse<Object>> addFavoriteGroup(
            @PathVariable UUID memberId,
            @RequestBody FavoriteRequest request) {
        Favorite favorite = new Favorite();
        favorite.setMemberId(memberId);
        favorite.setGroupName(request.getGroupName());
        Favorite savedFavorite = favoriteService.saveFavorite(favorite);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심그룹 추가 성공", savedFavorite));
    }

    @PutMapping("/{memberId}/{groupId}")
    @Operation(summary = "관심 그룹 이름 수정", description = "회원 ID와 그룹 ID로 관심 그룹 이름을 변경합니다.")
    public ResponseEntity<SuccessResponse<Object>> changeFavoriteGroupName(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId,
            @RequestBody FavoriteRequest request
    ) {
        try {
            Favorite updatedFavorite = favoriteService.updateFavoriteGroupName(memberId, groupId, request.getGroupName());
            return ResponseEntity.ok(new SuccessResponse<>(true, "관심그룹 이름 변경 성공", updatedFavorite));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SuccessResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{memberId}/{groupId}")
    @Operation(summary = "관심 그룹 삭제", description = "회원 ID와 그룹 ID로 관심 그룹을 삭제합니다.")
    public ResponseEntity<SuccessResponse<Object>> deleteFavoriteGroup(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId) {
        favoriteService.deleteFavoriteByMemberIdAndGroupId(memberId, groupId);
        return ResponseEntity.ok(new SuccessResponse<>(true, "관심그룹 제거 성공", null));
    }

    @PutMapping("/{memberId}/{groupId}/main")
    @Operation(summary = "관심 그룹 메인 변경", description = "회원 ID와 그룹 ID로 메인 관심 그룹을 변경합니다.")
    public ResponseEntity<SuccessResponse<Object>> setMainFavorite(
            @PathVariable UUID memberId,
            @PathVariable UUID groupId) {
        Optional<Favorite> updatedFavoriteOptional = favoriteService.setMainFavoriteGroup(memberId, groupId);
        if (updatedFavoriteOptional.isPresent()) {
            Favorite updatedFavorite = updatedFavoriteOptional.get();
            System.out.println("Successfully set main for groupId: " + updatedFavorite.getGroupId() + ", main: " + updatedFavorite.isMain()); // isMain() 사용
            return ResponseEntity.ok(new SuccessResponse<>(true, "주요 관심그룹 설정 성공", updatedFavorite)); // 변경된 객체 반환하거나 null
        } else {
            System.out.println("Favorite group not found for memberId=" + memberId + ", groupId=" + groupId + ". Cannot set main.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse<>(false, "해당 관심그룹을 찾을 수 없습니다.", null));
        }
    }

}
