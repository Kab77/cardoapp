package com.connecteam.controller;

import com.connecteam.model.RailwayInstallation;
import com.connecteam.model.RailwayInstallation.InstallationType;
import com.connecteam.service.RailwayInstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/installations")
public class RailwayInstallationController {

    @Autowired
    private RailwayInstallationService installationService;

    @PostMapping
    public ResponseEntity<RailwayInstallation> createInstallation(@RequestBody RailwayInstallation installation) {
        return ResponseEntity.ok(installationService.createInstallation(installation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RailwayInstallation> updateInstallation(
            @PathVariable Long id,
            @RequestBody RailwayInstallation installation) {
        return ResponseEntity.ok(installationService.updateInstallation(id, installation));
    }

    @GetMapping
    public ResponseEntity<List<RailwayInstallation>> getAllInstallations() {
        return ResponseEntity.ok(installationService.getAllInstallations());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RailwayInstallation>> getInstallationsByType(@PathVariable InstallationType type) {
        return ResponseEntity.ok(installationService.getInstallationsByType(type));
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<RailwayInstallation>> getInstallationsByZone(@PathVariable String zone) {
        return ResponseEntity.ok(installationService.getInstallationsByZone(zone));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<RailwayInstallation>> getInstallationsByCity(@PathVariable String city) {
        return ResponseEntity.ok(installationService.getInstallationsByCity(city));
    }

    @GetMapping("/active")
    public ResponseEntity<List<RailwayInstallation>> getActiveInstallations() {
        return ResponseEntity.ok(installationService.getActiveInstallations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RailwayInstallation> getInstallationById(@PathVariable Long id) {
        return ResponseEntity.ok(installationService.getInstallationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstallation(@PathVariable Long id) {
        installationService.deleteInstallation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/document")
    public ResponseEntity<String> uploadServiceDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(installationService.uploadServiceDocument(id, file));
    }

    @DeleteMapping("/{id}/document")
    public ResponseEntity<Void> deleteServiceDocument(@PathVariable Long id) {
        installationService.deleteServiceDocument(id);
        return ResponseEntity.ok().build();
    }
} 