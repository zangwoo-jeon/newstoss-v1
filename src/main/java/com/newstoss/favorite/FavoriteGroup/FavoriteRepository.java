package com.newstoss.favorite.FavoriteGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    List<Favorite> findByMemberId(UUID memberId);
    void deleteByMemberIdAndGroupId(UUID memberId, UUID groupId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Favorite f SET f.main = false WHERE f.memberId = :memberId")
    void resetMainStatusByMemberId(UUID memberId);

    Optional<Favorite> findByMemberIdAndGroupId(UUID memberId, UUID groupId);

    @Query("SELECT MAX(f.groupSequence) FROM Favorite f WHERE f.memberId = :memberId")
    Integer findMaxGroupSequenceByMemberId(UUID memberId);

    @Query("SELECT f FROM Favorite f WHERE f.memberId = :memberId ORDER BY f.groupSequence ASC")
    List<Favorite> findByMemberIdOrderByGroupSequenceAsc(@Param("memberId") UUID memberId);
}