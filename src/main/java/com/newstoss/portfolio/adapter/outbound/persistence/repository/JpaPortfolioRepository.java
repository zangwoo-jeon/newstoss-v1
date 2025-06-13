package com.newstoss.portfolio.adapter.outbound.persistence.repository;

import com.newstoss.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByMemberId(UUID memberId);
}
