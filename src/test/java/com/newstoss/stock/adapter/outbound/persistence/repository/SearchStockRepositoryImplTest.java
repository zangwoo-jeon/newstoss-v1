package com.newstoss.stock.adapter.outbound.persistence.repository;

import com.newstoss.stock.entity.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchStockRepositoryImplTest {
    
    @Autowired StockRepository stockRepository;   
    @Test
    public void searchStock() {
        //given
        String query = "삼성";
        //when
        List<Stock> stocks = stockRepository.searchStock(query);
        //then
        assertThat(stocks).isNotNull();
        for (Stock stock : stocks) {
            System.out.println("stock.getName() = " + stock.getName());
        }
    }

}