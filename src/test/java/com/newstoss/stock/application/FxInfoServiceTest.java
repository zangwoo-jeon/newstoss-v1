package com.newstoss.stock.application;

import com.newstoss.stock.adapter.inbound.dto.response.FxResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FxInfoServiceTest {

    @Autowired
    FxInfoService service;

    @Test
    public void FxServiceTest() {
        //given

        String type = "FX";
        String name = "dollar";

        //when
        FxResponseDto fxResponseDto = service.CurrentFxInfo(type, name);
        //then
        System.out.println("fxResponseDto = " + fxResponseDto);
    }

}