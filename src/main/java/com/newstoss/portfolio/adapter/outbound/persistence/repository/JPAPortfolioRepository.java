package com.newstoss.portfolio.adapter.outbound.persistence.repository;

import com.newstoss.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JPAPortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("SELECT p FROM Portfolio p join fetch p.stock s WHERE p.memberId = :memberId AND s.stockCode = :stockCode")
    Optional<Portfolio> findByMemberIdAndStockCode(UUID memberId, String stockCode);

    @Query("SELECT p FROM Portfolio p join fetch p.stock s WHERE p.memberId = :memberId")
    List<Portfolio> findByMemberId(UUID memberId);
}
