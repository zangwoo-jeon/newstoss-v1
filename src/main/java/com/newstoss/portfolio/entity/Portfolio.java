package com.newstoss.portfolio.entity;

import com.newstoss.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "portfolio")
public class Portfolio extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    private Long unrealized_pnl;
    private Long realized_pnl;

    @Column(name = "asset")
    private Long asset;

    @Column(name = "member_id")
    private UUID memberId;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioStock> portfolioStocks = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Portfolio createPortfolio(UUID memberId, Long unrealized_pnl, Long realized_pnl, Long asset) {
        Portfolio portfolio = new Portfolio();
        portfolio.realized_pnl = realized_pnl;
        portfolio.unrealized_pnl = unrealized_pnl;
        portfolio.asset = asset;
        portfolio.memberId = memberId;
        return portfolio;
    }

    //== 연관 관계 메서드 ==//
    public void addPortfolioStock(PortfolioStock portfolioStock) {
        this.portfolioStocks.add(portfolioStock);
    }

    //== 비즈니스 메서드 ==//

    /**
     * 자산을 업데이트합니다. 매개변수를 원래 있던 자산에 더합니다.
     * @param asset 변화량
     */
    public void updateAsset(Long asset) {
        this.asset += asset;
    }
    public void initUnrealizedPnl(Long pnl) {
        this.unrealized_pnl = pnl;
    }
    public void updateRealizedPnl(Long pnl) {
        this.realized_pnl += pnl;
    }
    public void removePortfolioStock(PortfolioStock portfolioStock) {
        portfolioStocks.remove(portfolioStock);
        portfolioStock.setPortfolio(null);
    }

}