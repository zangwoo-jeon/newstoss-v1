package com.newstoss.stock.adapter.outbound.persistence.init;

import com.newstoss.stock.adapter.outbound.kis.GetStockClient;
import com.newstoss.stock.adapter.outbound.kis.dto.KisPeriodStockDto;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockHistoryRepository;
import com.newstoss.stock.adapter.outbound.persistence.repository.StockRepository;
import com.newstoss.stock.entity.Stock;
import com.newstoss.stock.entity.StockHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockHistoryInitializer implements CommandLineRunner {

    private final GetStockClient getStockClient;
    private final StockHistoryRepository stockHistoryRepository;
    private final StockRepository stockRepository;

    @Value("${init.enabled}")
    private boolean initEnabled = true;
    @Override
    public void run(String... args) throws Exception {
        if (!initEnabled) return;
        List<Stock> stocks = stockRepository.findAll();
        log.info("총 주식 수 : {}", stocks.size());
        for (Stock stock : stocks) {
            String stockCode = stock.getStockCode();

            List<String> types = List.of("D", "W", "M", "Y");
            for (String type : types) {
                LocalDate startDate = null;
                LocalDate endDate = LocalDate.now();
                while (true) {
                    switch (type) {
                        case "D":
                            startDate = endDate.minusDays(100);
                            break;
                        case "W":
                            startDate = endDate.minusWeeks(100);
                            break;
                        case "M":
                            startDate = endDate.minusMonths(100);
                            break;
                        case "Y":
                            startDate = endDate.minusYears(100);
                            break;
                        default:
                            break;
                    }
                    List<KisPeriodStockDto> stockInfoByPeriod = getStockClient.getStockInfoByPeriod(stockCode, type, startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")), endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                    if (stockInfoByPeriod == null || stockInfoByPeriod.isEmpty()) break;

                    List<StockHistory> histories = stockInfoByPeriod.stream()
                            .map(dto -> StockHistory.createHistory(stockCode, type, dto))
                            .filter(history -> !stockHistoryRepository.existsByStockCodeAndTypeAndDate(
                                    history.getStockCode(),
                                    history.getType(),
                                    history.getDate()
                            ))
                            .collect(Collectors.toList());

                    if (!histories.isEmpty()) {
                        stockHistoryRepository.saveAll(histories);
                    }

                    endDate = startDate.minusDays(1);
                }
            }

        }
    }
}