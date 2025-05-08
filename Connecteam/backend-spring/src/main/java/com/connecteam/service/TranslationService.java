package com.connecteam.service;

import com.connecteam.model.Translation;
import com.connecteam.model.Language;
import com.connecteam.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class TranslationService {

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private LanguageService languageService;

    public Translation createTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    public List<Translation> getTranslationsByLanguage(String languageCode) {
        Language language = languageService.getLanguageByCode(languageCode);
        return translationRepository.findByLanguage(language);
    }

    public Map<String, String> getTranslationsByCategory(String languageCode, String category) {
        Language language = languageService.getLanguageByCode(languageCode);
        List<Translation> translations = translationRepository.findByLanguageAndCategory(language, category);
        
        Map<String, String> translationMap = new HashMap<>();
        for (Translation translation : translations) {
            translationMap.put(translation.getKey(), translation.getValue());
        }
        return translationMap;
    }

    public String getTranslation(String languageCode, String key) {
        Language language = languageService.getLanguageByCode(languageCode);
        return translationRepository.findByLanguageAndKey(language, key)
            .map(Translation::getValue)
            .orElse(key); // Retourne la clé si la traduction n'existe pas
    }

    public void initializeDefaultTranslations() {
        Language french = languageService.getLanguageByCode("fr");
        Language dutch = languageService.getLanguageByCode("nl");

        // Traductions pour l'interface utilisateur
        createUITranslations(french, dutch);
        
        // Traductions pour les emails
        createEmailTranslations(french, dutch);
        
        // Traductions pour les erreurs
        createErrorTranslations(french, dutch);
    }

    private void createUITranslations(Language french, Language dutch) {
        // Français
        createTranslation(new Translation("welcome", "Bienvenue", french, "UI"));
        createTranslation(new Translation("planning", "Planning", french, "UI"));
        createTranslation(new Translation("services", "Services", french, "UI"));
        createTranslation(new Translation("employees", "Employés", french, "UI"));
        createTranslation(new Translation("clients", "Clients", french, "UI"));
        createTranslation(new Translation("settings", "Paramètres", french, "UI"));

        // Néerlandais
        createTranslation(new Translation("welcome", "Welkom", dutch, "UI"));
        createTranslation(new Translation("planning", "Planning", dutch, "UI"));
        createTranslation(new Translation("services", "Diensten", dutch, "UI"));
        createTranslation(new Translation("employees", "Medewerkers", dutch, "UI"));
        createTranslation(new Translation("clients", "Klanten", dutch, "UI"));
        createTranslation(new Translation("settings", "Instellingen", dutch, "UI"));
    }

    private void createEmailTranslations(Language french, Language dutch) {
        // Français
        createTranslation(new Translation("email_subject_report", "Rapport quotidien", french, "EMAIL"));
        createTranslation(new Translation("email_greeting", "Bonjour", french, "EMAIL"));
        createTranslation(new Translation("email_footer", "Cordialement", french, "EMAIL"));

        // Néerlandais
        createTranslation(new Translation("email_subject_report", "Dagelijks rapport", dutch, "EMAIL"));
        createTranslation(new Translation("email_greeting", "Hallo", dutch, "EMAIL"));
        createTranslation(new Translation("email_footer", "Met vriendelijke groeten", dutch, "EMAIL"));
    }

    private void createErrorTranslations(Language french, Language dutch) {
        // Français
        createTranslation(new Translation("error_not_found", "Ressource non trouvée", french, "ERROR"));
        createTranslation(new Translation("error_unauthorized", "Non autorisé", french, "ERROR"));
        createTranslation(new Translation("error_validation", "Erreur de validation", french, "ERROR"));

        // Néerlandais
        createTranslation(new Translation("error_not_found", "Bron niet gevonden", dutch, "ERROR"));
        createTranslation(new Translation("error_unauthorized", "Niet geautoriseerd", dutch, "ERROR"));
        createTranslation(new Translation("error_validation", "Validatiefout", dutch, "ERROR"));
    }
} 