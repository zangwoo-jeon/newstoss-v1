package com.newstoss.favorite.FavoriteStock;

import com.newstoss.favorite.FavoriteGroup.Favorite;
import com.newstoss.stock.entity.Stock;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private Integer stockSequence;

    //== 생성 메서드 ==//
    public static FavoriteStock createFavoriteStock(Favorite favorite, Stock stock) {
        FavoriteStock favoriteStock = new FavoriteStock();
        favoriteStock.favorite = favorite;
        favoriteStock.stock = stock;
        favorite.getFavoriteStocks().add(favoriteStock);
        return favoriteStock;
    }
}
