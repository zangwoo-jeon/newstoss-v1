package com.newstoss.portfolio.entity;

import com.newstoss.global.auditing.BaseTimeEntity;
import com.newstoss.global.errorcode.PortfolioErrorCode;
import com.newstoss.global.handler.CustomException;
import com.newstoss.member.domain.Member;
import com.newstoss.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "portfolio_id")
    private Long id;

    private Integer stockCount;

    private Float entryPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolios")
    private Stock stock;

    //== 생성 메서드 ==//
    public static Portfolio createPortfolio(Member member, Integer stockCount, Float entryPrice) {
        Portfolio portfolio = new Portfolio();
        portfolio.member = member;
        portfolio.stockCount = stockCount;
        portfolio.entryPrice = entryPrice;
        return portfolio;
    }

    //== 비즈니스 로직 ==//
    public void addStock(Integer stockCount, Float entryPrice) {
        this.entryPrice = (this.entryPrice * this.stockCount + entryPrice * stockCount) / (this.stockCount+ stockCount);
        this.stockCount += stockCount;
    }

    public Float removeStock(Integer stockCount, Integer currentPrice) {
        if (this.stockCount < stockCount) {
            throw new CustomException(PortfolioErrorCode.PORTFOLIO_STOCK_QUANTITY_NOT_ENOUGH);
        }
        return (currentPrice - this.entryPrice) * stockCount;
    }

    //== 연관관계 메서드==//

}
