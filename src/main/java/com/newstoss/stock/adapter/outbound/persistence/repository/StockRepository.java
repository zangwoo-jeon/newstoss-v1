package com.newstoss.stock.adapter.outbound.persistence.repository;

import com.newstoss.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT DISTINCT s.category FROM Stock s order by s.category")
    List<String> findCategoryAll();

    @Query("SELECT s FROM Stock s WHERE s.category = :category")
    List<Stock> findStockCodeByCategory(@Param("category") String category);
}
