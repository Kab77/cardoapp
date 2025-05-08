package com.connecteam.controller;

import com.connecteam.model.EmailConfig;
import com.connecteam.repository.EmailConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/email-config")
public class EmailConfigController {

    @Autowired
    private EmailConfigRepository emailConfigRepository;

    @PostMapping
    public ResponseEntity<EmailConfig> createEmailConfig(@Valid @RequestBody EmailConfig config) {
        // Désactiver toutes les autres configurations
        List<EmailConfig> existingConfigs = emailConfigRepository.findAll();
        for (EmailConfig existingConfig : existingConfigs) {
            existingConfig.setActive(false);
            emailConfigRepository.save(existingConfig);
        }
        
        config.setActive(true);
        return ResponseEntity.ok(emailConfigRepository.save(config));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailConfig> updateEmailConfig(
            @PathVariable Long id,
            @Valid @RequestBody EmailConfig config) {
        EmailConfig existingConfig = emailConfigRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Configuration non trouvée"));
            
        existingConfig.setSmtpHost(config.getSmtpHost());
        existingConfig.setSmtpPort(config.getSmtpPort());
        existingConfig.setUsername(config.getUsername());
        existingConfig.setPassword(config.getPassword());
        existingConfig.setFromEmail(config.getFromEmail());
        existingConfig.setToEmail(config.getToEmail());
        existingConfig.setActive(config.isActive());
        
        return ResponseEntity.ok(emailConfigRepository.save(existingConfig));
    }

    @GetMapping
    public ResponseEntity<List<EmailConfig>> getAllEmailConfigs() {
        return ResponseEntity.ok(emailConfigRepository.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<EmailConfig> getActiveEmailConfig() {
        return ResponseEntity.ok(emailConfigRepository.findByIsActiveTrue()
            .orElseThrow(() -> new RuntimeException("Aucune configuration active trouvée")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailConfig(@PathVariable Long id) {
        emailConfigRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 