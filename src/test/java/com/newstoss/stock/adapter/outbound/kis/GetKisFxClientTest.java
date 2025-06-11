package com.newstoss.stock.adapter.outbound.kis;

import com.newstoss.stock.adapter.inbound.dto.response.FxResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetKisFxClientTest {

    @Autowired
    GetKisFxClient client;

    @Test
    public void FXclientTest() {
        //given
        String code1 = "I";
        String symbol1 = "Y0104";

        String code2 = "X";
        String symbol2 = "VXZ";

        //when
        FxResponseDto fxResponseDto = client.FxInfo(code1, symbol1);
        //then
        System.out.println("fxResponseDto = " + fxResponseDto);

    }

}