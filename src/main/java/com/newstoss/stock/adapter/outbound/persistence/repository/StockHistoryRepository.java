package com.newstoss.stock.adapter.outbound.persistence.repository;

import com.newstoss.stock.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    boolean existsByStockCodeAndTypeAndDate(String stockCode, String type, LocalDate date);
}