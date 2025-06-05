package com.newstoss.portfolio.entity;

import com.newstoss.global.auditing.BaseTimeEntity;
import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "portfolio_id")
    private Long id;

    private Integer stockCount;

    private Integer entryPrice;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    //== 생성 메서드 ==//
    public static Portfolio createPortfolio(UUID memberId, Stock stock, Integer stockCount, Integer entryPrice) {
        Portfolio portfolio = new Portfolio();
        portfolio.memberId = memberId;
        portfolio.setStock(stock);
        portfolio.stockCount = stockCount;
        portfolio.entryPrice = entryPrice;
        return portfolio;
    }

    //== 비즈니스 로직 ==//
    public void addStock(Integer stockCount, Integer entryPrice) {
        this.entryPrice = (this.entryPrice * this.stockCount + entryPrice * stockCount) / (this.stockCount + stockCount);
        this.stockCount += stockCount;
    }

    public Integer removeStock(Integer stockCount, Integer currentPrice) {
        if (this.stockCount < stockCount) {
            throw new CustomException(PortfolioErrorCode.PORTFOLIO_STOCK_QUANTITY_NOT_ENOUGH);
        }
        this.stockCount -= stockCount;
        return (currentPrice - this.entryPrice) * stockCount;
    }

    //== 연관관계 메서드==//

    public void setStock(Stock stock) {
        this.stock = stock;
        if (!stock.getPortfolios().contains(this)) {
            stock.getPortfolios().add(this);
        }
    }
}
