package com.newstoss.favorite.FavoriteStock;

import com.newstoss.favorite.FavoriteGroup.Favorite;
import com.newstoss.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteStockRepository extends JpaRepository<FavoriteStock, Long> {
    @Query("SELECT fs FROM FavoriteStock fs WHERE fs.favorite.memberId = :memberId AND fs.favorite.groupId = :groupId ORDER BY fs.stockSequence ASC")
    List<FavoriteStock> findByMemberIdAndGroupIdOrderByStockSequenceAsc(@Param("memberId") UUID memberId, @Param("groupId") UUID groupId);

    boolean existsByFavoriteAndStock(Favorite favorite, Stock stock);

    @Query("SELECT MAX(fs.stockSequence) FROM FavoriteStock fs WHERE fs.favorite = :favorite")
    Integer findMaxStockSequenceByFavorite(@Param("favorite") Favorite favorite);

    @Query("SELECT fs FROM FavoriteStock fs WHERE fs.favorite = :favorite AND fs.stock = :stock")
    Optional<FavoriteStock> findByFavoriteAndStock(@Param("favorite") Favorite favorite, @Param("stock") Stock stock);

    @Modifying
    @Query("UPDATE FavoriteStock fs SET fs.stockSequence = fs.stockSequence - 1 WHERE fs.favorite = :favorite AND fs.stockSequence > :deletedSequence")
    void decrementStockSequenceAfter(@Param("favorite") Favorite favorite, @Param("deletedSequence") Integer deletedSequence);

    @Modifying
    @Query("UPDATE FavoriteStock fs SET fs.stockSequence = fs.stockSequence - 1 WHERE fs.favorite = :favorite AND fs.stockSequence BETWEEN :start AND :end")
    void decrementStockSequenceBetween(@Param("favorite") Favorite favorite, @Param("start") Integer start, @Param("end") Integer end);

    @Modifying
    @Query("UPDATE FavoriteStock fs SET fs.stockSequence = fs.stockSequence + 1 WHERE fs.favorite = :favorite AND fs.stockSequence BETWEEN :start AND :end")
    void incrementStockSequenceBetween(@Param("favorite") Favorite favorite, @Param("start") Integer start, @Param("end") Integer end);

    @Modifying
    @Query("DELETE FROM FavoriteStock fs WHERE fs.favorite.groupId = :groupId")
    void deleteByFavoriteGroupId(@Param("groupId") UUID groupId);
}
