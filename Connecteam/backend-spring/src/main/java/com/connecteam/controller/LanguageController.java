package com.connecteam.controller;

import com.connecteam.model.Language;
import com.connecteam.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @PostMapping
    public ResponseEntity<Language> createLanguage(@RequestBody Language language) {
        return ResponseEntity.ok(languageService.createLanguage(language));
    }

    @GetMapping("/default")
    public ResponseEntity<Language> getDefaultLanguage() {
        return ResponseEntity.ok(languageService.getDefaultLanguage());
    }

    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Language> getLanguageByCode(@PathVariable String code) {
        return ResponseEntity.ok(languageService.getLanguageByCode(code));
    }
} 