package com.newstoss.stock.adapter.inbound.api.v1;

import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.entity.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StockApiControllerTest {
    @Autowired
    StockRepository stockRepository;
    @Test
    public void stockCountIncrease() {
        //given
        String stockName1 = "삼성전자";
        String stockName2 = "LG전자";
        String stockName3 = "카카오";
        String stockName4 = "NAVER";
        String stockName5 = "현대차";
        String stockName6 = "SK하이닉스";

        //when
        stockRepository.findByName(stockName1)
                .ifPresent(Stock::incrementStockSearchCount);
        stockRepository.findByName(stockName2)
                .ifPresent(Stock::incrementStockSearchCount);
        stockRepository.findByName(stockName3)
                .ifPresent(Stock::incrementStockSearchCount);
        stockRepository.findByName(stockName4)
                .ifPresent(Stock::incrementStockSearchCount);
        stockRepository.findByName(stockName6)
                .ifPresent(Stock::incrementStockSearchCount);

        //then
    }
}