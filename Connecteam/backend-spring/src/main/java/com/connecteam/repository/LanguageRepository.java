package com.connecteam.repository;

import com.connecteam.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByCode(String code);
    Optional<Language> findByIsDefaultTrue();
} 