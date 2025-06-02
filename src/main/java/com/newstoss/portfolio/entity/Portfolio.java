package com.newstoss.portfolio.entity;

import com.newstoss.global.auditing.BaseTimeEntity;
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

    private Integer entryPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "portfolio")
    private Stock stock;

    //== 생성 메서드 ==//
    public static Portfolio createPortfolio(Member member, Integer stockCount, Integer entryPrice) {
        Portfolio portfolio = new Portfolio();
        portfolio.member = member;
        portfolio.stockCount = stockCount;
        portfolio.entryPrice = entryPrice;
        return portfolio;
    }

    //== 비즈니스 로직 ==//
    public void addStock(Stock stock, Integer stockCount, Integer entryPrice) {

    }
}
