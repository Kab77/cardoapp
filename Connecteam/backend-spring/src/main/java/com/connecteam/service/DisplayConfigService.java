package com.connecteam.service;

import com.connecteam.model.DisplayConfig;
import com.connecteam.repository.DisplayConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DisplayConfigService {

    @Autowired
    private DisplayConfigRepository displayConfigRepository;

    public DisplayConfig createDisplayConfig(DisplayConfig config) {
        if (config.isDefault()) {
            // Désactiver toutes les autres configurations par défaut
            List<DisplayConfig> existingConfigs = displayConfigRepository.findAll();
            for (DisplayConfig existingConfig : existingConfigs) {
                if (existingConfig.isDefault()) {
                    existingConfig.setDefault(false);
                    displayConfigRepository.save(existingConfig);
                }
            }
        }
        return displayConfigRepository.save(config);
    }

    public DisplayConfig updateDisplayConfig(Long id, DisplayConfig config) {
        DisplayConfig existingConfig = displayConfigRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Configuration non trouvée"));

        if (config.isDefault() && !existingConfig.isDefault()) {
            // Désactiver toutes les autres configurations par défaut
            List<DisplayConfig> existingConfigs = displayConfigRepository.findAll();
            for (DisplayConfig otherConfig : existingConfigs) {
                if (otherConfig.isDefault()) {
                    otherConfig.setDefault(false);
                    displayConfigRepository.save(otherConfig);
                }
            }
        }

        // Mettre à jour les propriétés
        existingConfig.setTheme(config.getTheme());
        existingConfig.setPrimaryColor(config.getPrimaryColor());
        existingConfig.setSecondaryColor(config.getSecondaryColor());
        existingConfig.setFontSize(config.getFontSize());
        existingConfig.setCompactView(config.isCompactView());
        existingConfig.setShowWeekends(config.isShowWeekends());
        existingConfig.setDefaultView(config.getDefaultView());
        existingConfig.setTimeFormat(config.getTimeFormat());
        existingConfig.setMobileOptimized(config.isMobileOptimized());
        existingConfig.setShowEmployeePhoto(config.isShowEmployeePhoto());
        existingConfig.setShowServiceColor(config.isShowServiceColor());
        existingConfig.setShowRemarksPreview(config.isShowRemarksPreview());
        existingConfig.setMaxVisibleServices(config.getMaxVisibleServices());
        existingConfig.setRefreshInterval(config.getRefreshInterval());
        existingConfig.setDefault(config.isDefault());

        return displayConfigRepository.save(existingConfig);
    }

    public DisplayConfig getDefaultConfig() {
        return displayConfigRepository.findByIsDefaultTrue()
            .orElseGet(() -> {
                // Créer une configuration par défaut si aucune n'existe
                DisplayConfig defaultConfig = new DisplayConfig();
                defaultConfig.setDefault(true);
                return displayConfigRepository.save(defaultConfig);
            });
    }

    public List<DisplayConfig> getAllConfigs() {
        return displayConfigRepository.findAll();
    }

    public void deleteConfig(Long id) {
        DisplayConfig config = displayConfigRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Configuration non trouvée"));
            
        if (config.isDefault()) {
            throw new RuntimeException("Impossible de supprimer la configuration par défaut");
        }
        
        displayConfigRepository.deleteById(id);
    }

    public DisplayConfig getMobileOptimizedConfig() {
        DisplayConfig config = getDefaultConfig();
        
        // Ajuster les paramètres pour une meilleure expérience mobile
        config.setCompactView(true);
        config.setMobileOptimized(true);
        config.setFontSize(16); // Taille de police plus grande pour mobile
        config.setMaxVisibleServices(3); // Moins de services visibles sur mobile
        config.setShowRemarksPreview(false); // Désactiver l'aperçu des remarques sur mobile
        
        return config;
    }
} 