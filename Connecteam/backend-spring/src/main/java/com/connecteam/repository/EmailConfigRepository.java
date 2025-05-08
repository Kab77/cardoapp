package com.connecteam.repository;

import com.connecteam.model.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmailConfigRepository extends JpaRepository<EmailConfig, Long> {
    Optional<EmailConfig> findByIsActiveTrue();
} 