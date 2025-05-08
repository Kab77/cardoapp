package com.connecteam.controller;

import com.connecteam.model.Translation;
import com.connecteam.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        return ResponseEntity.ok(translationService.createTranslation(translation));
    }

    @GetMapping("/language/{languageCode}")
    public ResponseEntity<List<Translation>> getTranslationsByLanguage(@PathVariable String languageCode) {
        return ResponseEntity.ok(translationService.getTranslationsByLanguage(languageCode));
    }

    @GetMapping("/language/{languageCode}/category/{category}")
    public ResponseEntity<Map<String, String>> getTranslationsByCategory(
            @PathVariable String languageCode,
            @PathVariable String category) {
        return ResponseEntity.ok(translationService.getTranslationsByCategory(languageCode, category));
    }

    @GetMapping("/language/{languageCode}/key/{key}")
    public ResponseEntity<String> getTranslation(
            @PathVariable String languageCode,
            @PathVariable String key) {
        return ResponseEntity.ok(translationService.getTranslation(languageCode, key));
    }

    @PostMapping("/initialize")
    public ResponseEntity<Void> initializeDefaultTranslations() {
        translationService.initializeDefaultTranslations();
        return ResponseEntity.ok().build();
    }
} 