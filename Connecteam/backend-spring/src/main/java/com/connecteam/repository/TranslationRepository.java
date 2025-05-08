package com.connecteam.repository;

import com.connecteam.model.Translation;
import com.connecteam.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findByLanguage(Language language);
    List<Translation> findByLanguageAndCategory(Language language, String category);
    Optional<Translation> findByLanguageAndKey(Language language, String key);
} 