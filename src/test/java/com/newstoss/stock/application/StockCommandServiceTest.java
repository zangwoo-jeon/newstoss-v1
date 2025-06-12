package com.newstoss.stock.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class StockCommandServiceTest {

    @Autowired
    private StockCommandService service;

    @Test
    public void createStockTest() {
        //given
        String stockCode = "0005A0";

        //when
        Long save = service.save(stockCode);
        //then
        Assertions.assertThat(save).isNotNull();
    }

}