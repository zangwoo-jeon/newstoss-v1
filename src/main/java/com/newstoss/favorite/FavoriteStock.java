package com.newstoss.favorite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "GroupStock")
@Getter
@Setter
@NoArgsConstructor
public class FavoriteStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteStockId;

    private UUID groupId;

    private Long stockId;



}
