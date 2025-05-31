package com.newstoss.global.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newstoss.stock.adapter.outbound.kis.dto.KisStockDto;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.application.port.in.GetStockInfoUseCase;
import com.newstoss.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockInit implements CommandLineRunner {
    private final StockRepository stockRepository;
    private final ObjectMapper objectMapper;
    private final GetStockInfoUseCase getStockInfoUseCase;

    @Value("${init.enabled}")
    private boolean initEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!initEnabled) return;
        else if (stockRepository.count() > 0) {
            throw new IllegalStateException("초기 데이터가 이미 존재합니다.");
        }

        List<Stock> stocks = new ArrayList<>();

        InputStream is = new ClassPathResource("data/company.json").getInputStream();
        List<StockInitDto> stockDtos = Arrays.asList(objectMapper.readValue(is, StockInitDto[].class));
        for (StockInitDto stockDto : stockDtos) {
            try {
                KisStockDto stockInfo = getStockInfoUseCase.GetStockInfo(stockDto.getStockCode());
                String setCategoryName;
                if (stockInfo.getCategoryName() == null || stockInfo.getCategoryName().equals(" ")) {
                    setCategoryName = "기타";
                } else {
                    setCategoryName = stockInfo.getCategoryName();
                }
                Stock stock = Stock.createStock(stockDto.getStockCode(), stockDto.getName(), null, stockInfo.getMarketName(), setCategoryName);
                stocks.add(stock);

                Thread.sleep(50);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        stockRepository.saveAll(stocks);
    }
}
