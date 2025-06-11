package com.newstoss.favorite.FavoriteGroup;

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

    public List<Favorite> getFavoritesByMemberId(UUID memberId) {
        return favoriteRepository.findByMemberId(memberId);
    }

    @Transactional
    public void saveFavorite(Favorite favorite) {
        if (favorite.isMain()) {
            favoriteRepository.resetMainStatusByMemberId(favorite.getMemberId());
        }
        
        // 현재 회원의 최대 sequence 값 조회
        Integer maxSequence = favoriteRepository.findMaxGroupSequenceByMemberId(favorite.getMemberId());
        int nextSequence = (maxSequence == null) ? 1 : maxSequence + 1;
        favorite.setGroupSequence(nextSequence);
        
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteFavoriteByMemberIdAndGroupId(UUID memberId, UUID groupId) {
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
} 