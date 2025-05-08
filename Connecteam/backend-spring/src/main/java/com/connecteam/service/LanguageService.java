package com.connecteam.service;

import com.connecteam.model.Language;
import com.connecteam.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public Language createLanguage(Language language) {
        if (language.isDefault()) {
            // Désactiver toutes les autres langues par défaut
            List<Language> existingLanguages = languageRepository.findAll();
            for (Language existingLanguage : existingLanguages) {
                if (existingLanguage.isDefault()) {
                    existingLanguage.setDefault(false);
                    languageRepository.save(existingLanguage);
                }
            }
        }
        return languageRepository.save(language);
    }

    public Language getDefaultLanguage() {
        return languageRepository.findByIsDefaultTrue()
            .orElseGet(() -> {
                // Créer le français comme langue par défaut si aucune n'existe
                Language defaultLanguage = new Language();
                defaultLanguage.setCode("fr");
                defaultLanguage.setName("Français");
                defaultLanguage.setDefault(true);
                return languageRepository.save(defaultLanguage);
            });
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageByCode(String code) {
        return languageRepository.findByCode(code)
            .orElseThrow(() -> new RuntimeException("Langue non trouvée"));
    }
} 