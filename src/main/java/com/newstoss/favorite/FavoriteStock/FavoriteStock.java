package com.newstoss.favorite.FavoriteStock;

import com.newstoss.favorite.FavoriteGroup.Favorite;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GroupStock")
@Getter
@Setter
@NoArgsConstructor
public class FavoriteStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteStockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Favorite favorite;

    @Column(name = "stock_code", nullable = false)
    private String stockCode;

    private Integer stockSequence;

    //== 생성 메서드 ==//
    public static FavoriteStock createFavoriteStock(Favorite favorite, String stockCode) {
        FavoriteStock favoriteStock = new FavoriteStock();
        favoriteStock.favorite = favorite;
        favoriteStock.stockCode = stockCode;
        return favoriteStock;
    }
}
