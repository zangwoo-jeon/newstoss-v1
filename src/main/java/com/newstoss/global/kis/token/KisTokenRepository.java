package com.newstoss.global.kis.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KisTokenRepository extends JpaRepository<KisToken, Long> {
    Optional<KisToken> findTopByOrderByIdDesc();
}
