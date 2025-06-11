package com.newstoss.stock.adapter.outbound.persistence.init;

import com.newstoss.stock.entity.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockInitializerTest {

    @Test
    public void initTest() throws IOException {
        //given
        InputStream is = getClass().getClassLoader().getResourceAsStream("data/kospi_code.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is , StandardCharsets.UTF_8));
        String line;
        int count = 0;

        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            if (tokens[0].length() == 6) {
                // 종목 코드가 앞 6자리
                String stockCode = line.substring(0, 6).trim();

                // ISIN 코드 + 종목명 추정 구간 (확장 가능)
                String isinWithName = line.substring(9, 80).trim();

                count++;
                String rawname = isinWithName.substring(12).trim(); // 하이트진로2우B, KODEX 인도Nifty미드캡100 등
                // "EF"로 끝나는 위치까지 자르기
                String name;
                int efIndex = rawname.indexOf("EF");
                if (efIndex != -1) {
                    name = rawname.substring(0, efIndex + 2).trim(); // EF 포함 자르기
                } else {
                    name = rawname.split("\\s{2,}")[0].trim(); // EF 없으면 전체
                }

                System.out.println(count + "번째 주식 코드: " + stockCode + " / 이름: " + name
                + " // ®이름 길이: " + name.length());

            }
        }
        //then
    }

}