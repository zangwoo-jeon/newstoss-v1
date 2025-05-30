package com.newstoss.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        favoriteRepository.save(favorite);
    }

} 