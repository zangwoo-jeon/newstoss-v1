package com.newstoss.portfolio.adapter.outbound.persistence.repository;

import com.newstoss.portfolio.entity.PortfolioStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPAPortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {

    @Query("SELECT p FROM PortfolioStock p WHERE p.memberId = :memberId AND p.stockCode = :stockCode")
    Optional<PortfolioStock> findByMemberIdAndStockCode(UUID memberId, String stockCode);

    @Query("SELECT p FROM PortfolioStock p WHERE p.memberId = :memberId")
    List<PortfolioStock> findByMemberId(UUID memberId);

}
