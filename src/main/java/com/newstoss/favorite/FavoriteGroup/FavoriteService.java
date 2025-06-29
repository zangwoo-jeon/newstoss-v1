package com.newstoss.favorite.FavoriteGroup;

import com.newstoss.favorite.FavoriteStock.FavoriteStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteStockRepository favoriteStockRepository;

    public List<Favorite> getFavoritesByMemberId(UUID memberId) {
        return favoriteRepository.findByMemberIdOrderByGroupSequenceAsc(memberId);
    }

    @Transactional
    public Favorite saveFavorite(Favorite favorite) {
        if (favorite.isMain()) {
            favoriteRepository.resetMainStatusByMemberId(favorite.getMemberId());
        }
        
        // 현재 회원의 최대 sequence 값 조회
        Integer maxSequence = favoriteRepository.findMaxGroupSequenceByMemberId(favorite.getMemberId());
        int nextSequence = (maxSequence == null) ? 1 : maxSequence + 1;
        favorite.setGroupSequence(nextSequence);
        
        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteFavoriteByMemberIdAndGroupId(UUID memberId, UUID groupId) {
        // 먼저 관련된 FavoriteStock들 삭제
        favoriteStockRepository.deleteByFavoriteGroupId(groupId);
        // 그 다음 Favorite 그룹 삭제
        favoriteRepository.deleteByMemberIdAndGroupId(memberId, groupId);
    }

    @Transactional
    public Optional<Favorite> setMainFavoriteGroup(UUID memberId, UUID groupId) {

        favoriteRepository.resetMainStatusByMemberId(memberId);

        Optional<Favorite> favoriteOptional = favoriteRepository.findByMemberIdAndGroupId(memberId, groupId);

        if (favoriteOptional.isPresent()) {
            Favorite favoriteToUpdate = favoriteOptional.get();
            favoriteToUpdate.setMain(true);
            return Optional.of(favoriteToUpdate);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public Favorite updateFavoriteGroupName(UUID memberId, UUID groupId, String newGroupName) {
        Favorite favorite = favoriteRepository.findByMemberIdAndGroupId(memberId, groupId)
                .orElseThrow(() -> new RuntimeException("해당 관심그룹을 찾을 수 없습니다."));
        
        favorite.setGroupName(newGroupName);
        return favoriteRepository.save(favorite);
    }
} 