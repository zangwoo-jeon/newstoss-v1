package com.newstoss.stock.adapter.outbound.persistence.repository;

import com.newstoss.stock.application.port.out.StockRepositoryCustom;
import com.newstoss.stock.entity.QStock;
import com.newstoss.stock.entity.Stock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.newstoss.stock.entity.QStock.*;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    /**
     * 검색어를 이용해 주식 정보를 조회하는 메서드
     *
     * @author Hyeongjun Park
     * @param query 검색어
     * @return 주식 정보 리스트
     */
    @Override
    public List<Stock> searchStock(String query) {
        return queryFactory
                .selectFrom(stock)
                .where(stock.name.contains(query))
                .orderBy(stock.stockSearchCount.desc())
                .limit(5)
                .fetch();
    }
}
