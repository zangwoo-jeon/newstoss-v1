package com.newstoss.stock.entity;

import com.newstoss.global.auditing.BaseTimeEntity;
import com.newstoss.portfolio.entity.Portfolio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "stock_name")
    private String name;

    @Column(name = "market_name")
    private String marketName;

    @Column(name = "stock_image")
    private String stockImage;

    private Integer stockSearchCount;
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    //== 생성 메서드 ==//
    public static Stock createStock(String stockCode, String name, Integer price, String marketName, String category) {
        Stock stock = new Stock();
        stock.stockCode = stockCode;
        stock.name = name;
        stock.marketName = marketName;
        stock.category = category;
        stock.stockSearchCount = 0; // 초기 검색 횟수는 0으로 설정
        return stock;
    }

    //== 비즈니스 로직 ==//
    public void incrementStockSearchCount() {
        if (stockSearchCount == null) {
            stockSearchCount = 0;
        }
        stockSearchCount++;
    }
}
