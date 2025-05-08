package com.connecteam.controller;

import com.connecteam.model.DisplayConfig;
import com.connecteam.service.DisplayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/display-config")
public class DisplayConfigController {

    @Autowired
    private DisplayConfigService displayConfigService;

    @PostMapping
    public ResponseEntity<DisplayConfig> createDisplayConfig(@Valid @RequestBody DisplayConfig config) {
        return ResponseEntity.ok(displayConfigService.createDisplayConfig(config));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisplayConfig> updateDisplayConfig(
            @PathVariable Long id,
            @Valid @RequestBody DisplayConfig config) {
        return ResponseEntity.ok(displayConfigService.updateDisplayConfig(id, config));
    }

    @GetMapping("/default")
    public ResponseEntity<DisplayConfig> getDefaultConfig() {
        return ResponseEntity.ok(displayConfigService.getDefaultConfig());
    }

    @GetMapping("/mobile")
    public ResponseEntity<DisplayConfig> getMobileConfig() {
        return ResponseEntity.ok(displayConfigService.getMobileOptimizedConfig());
    }

    @GetMapping
    public ResponseEntity<List<DisplayConfig>> getAllConfigs() {
        return ResponseEntity.ok(displayConfigService.getAllConfigs());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable Long id) {
        displayConfigService.deleteConfig(id);
        return ResponseEntity.ok().build();
    }
} 