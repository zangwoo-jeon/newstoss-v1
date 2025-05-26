package com.newstoss.global.kis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KisTokenRepository extends JpaRepository<KisToken, Long> {
    Optional<KisToken> findTopByOrderByIdDesc();
}
