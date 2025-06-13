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
@Table(name = "portfolio_stock", schema = "test_schema")
public class PortfolioStock extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "portfolio_stock_id")
    private Long id;

    private Integer stockCount;

    private Integer entryPrice;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Column(name = "unrealized_pnl")
    private Long unrealizedPnl;

    @Column(name = "realized_pnl")
    private Long realizedPnl;

    //== 생성 메서드 ==//
    public static PortfolioStock createPortfolio(UUID memberId,Portfolio portfolio , Stock stock, Integer stockCount, Integer entryPrice) {
        PortfolioStock portfolioStock = new PortfolioStock();
        portfolioStock.memberId = memberId;
        portfolioStock.setPortfolio(portfolio);
        portfolioStock.setPortfolioStock(stock);
        portfolioStock.stockCount = stockCount;
        portfolioStock.entryPrice = entryPrice;
        return portfolioStock;
    }

    //== 비즈니스 로직 ==//
    public PortfolioStock addStock(Integer stockCount, Integer entryPrice) {
        this.entryPrice = (this.entryPrice * this.stockCount + entryPrice * stockCount) / (this.stockCount + stockCount);
        this.stockCount += stockCount;
        return this;
    }

    public Long removeStock(Integer stockCount, Integer currentPrice) {
        if (this.stockCount < stockCount) {
            throw new CustomException(PortfolioErrorCode.PORTFOLIO_STOCK_QUANTITY_NOT_ENOUGH);
        }
        this.stockCount -= stockCount;
        realizedPnl += (long) (currentPrice - this.entryPrice) * stockCount;
        return realizedPnl;
    }

    /**
     * 실현 손익을 업데이트합니다. <br> 더하기 하는것
     * @param pnl 업데이트할 pnl
     * @return 업데이트 된 pnl
     */
    public Long updateRealizedPnl(Long pnl) {
        unrealizedPnl = pnl;
        return unrealizedPnl;
    }

    /**
     * 미실현 손익을 업데이트합니다. <br> 더하기 하는것
     * @param pnl 업데이트할 pnl
     * @return 업데이트 된 pnl
     */
    public Long updateUnRealizedPnl(Long pnl) {
        realizedPnl += pnl;
        return realizedPnl;
    }

    public void initRealizedPnl(Long pnl) {
        realizedPnl = pnl;
    }

    public void initUnRealizedPnl(Long pnl) {
        unrealizedPnl = pnl;
    }


    //== 연관관계 메서드==//

    public void setPortfolioStock(Stock stock) {
        this.stock = stock;
    }

    public void setPortfolio( Portfolio portfolio) {
        this.portfolio = portfolio;
        if (portfolio != null && !portfolio.getPortfolioStocks().contains(this)) {
            portfolio.getPortfolioStocks().add(this);
        }
    }
}
