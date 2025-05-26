package com.newstoss.stock.entity;

import com.newstoss.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "stock_name")
    private String name;

    @Column(name = "stock_price")
    private Integer price;

    @Column(name = "market_name")
    private String marketName;

    private String category;

    //== 생성 메서드 ==//
    public static Stock createStock(String stockCode, String name, Integer price, String marketName, String category) {
        Stock stock = new Stock();
        stock.stockCode = stockCode;
        stock.name = name;
        stock.price = price;
        stock.marketName = marketName;
        stock.category = category;
        return stock;
    }

    //== 비즈니스 로직 ==//
    public void updatePrice(Integer price) {
        this.price = price;
    }

}
