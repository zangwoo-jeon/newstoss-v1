package com.newstoss.stock.adapter.outbound.persistence.init;

import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.CreateStockUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockInitializer implements CommandLineRunner {

    private final CreateStockUseCase createStockUseCase;
    private final StockRepository stockRepository;

    @Value("${init.enabled}")
    private boolean initEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!initEnabled) return;
        InputStream is = getClass().getClassLoader().getResourceAsStream("data/kospi_code.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is , StandardCharsets.UTF_8));
        String line;
        int count = 0;

        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split("\\s+");
            if (tokens[0].length() == 6) {
                String stockCode = tokens[0];
                if (stockRepository.findByStockCode(stockCode).isEmpty()) {
                    createStockUseCase.save(stockCode);
                    Thread.sleep(50);
                }
            }

        }
    }
}
