package com.newstoss.stock.adapter.outbound.persistence.repository;

import com.newstoss.stock.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> , StockSearchRepository {

    @Query("SELECT DISTINCT s.category FROM Stock s order by s.category")
    List<String> findCategoryAll();

    @Query("SELECT s FROM Stock s WHERE s.category = :category")
    Page<Stock> findStockCodeByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT s FROM Stock s order by s.stockSearchCount DESC LIMIT 5")
    List<Stock> findStockByStockSearchCount();

    Optional<Stock> findByName(String name);

    Optional<Stock> findByStockCode(String code);
}
