package com.connecteam.repository;

import com.connecteam.model.DisplayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DisplayConfigRepository extends JpaRepository<DisplayConfig, Long> {
    Optional<DisplayConfig> findByIsDefaultTrue();
} 