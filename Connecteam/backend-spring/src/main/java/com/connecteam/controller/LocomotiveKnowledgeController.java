package com.connecteam.controller;

import com.connecteam.model.LocomotiveKnowledge;
import com.connecteam.service.LocomotiveKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/locomotive-knowledge")
public class LocomotiveKnowledgeController {

    @Autowired
    private LocomotiveKnowledgeService locomotiveKnowledgeService;

    @PostMapping
    public ResponseEntity<LocomotiveKnowledge> createLocomotiveKnowledge(@RequestBody LocomotiveKnowledge knowledge) {
        return ResponseEntity.ok(locomotiveKnowledgeService.createLocomotiveKnowledge(knowledge));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocomotiveKnowledge> updateLocomotiveKnowledge(
            @PathVariable Long id,
            @RequestBody LocomotiveKnowledge knowledge) {
        return ResponseEntity.ok(locomotiveKnowledgeService.updateLocomotiveKnowledge(id, knowledge));
    }

    @GetMapping
    public ResponseEntity<List<LocomotiveKnowledge>> getAllLocomotiveKnowledge() {
        return ResponseEntity.ok(locomotiveKnowledgeService.getAllLocomotiveKnowledge());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<LocomotiveKnowledge>> getLocomotiveKnowledgeByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(locomotiveKnowledgeService.getLocomotiveKnowledgeByClient(clientId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<LocomotiveKnowledge>> getLocomotiveKnowledgeByType(@PathVariable String type) {
        return ResponseEntity.ok(locomotiveKnowledgeService.getLocomotiveKnowledgeByType(type));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LocomotiveKnowledge>> getLocomotiveKnowledgeByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(locomotiveKnowledgeService.getLocomotiveKnowledgeByEmployee(employeeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocomotiveKnowledge> getLocomotiveKnowledgeById(@PathVariable Long id) {
        return ResponseEntity.ok(locomotiveKnowledgeService.getLocomotiveKnowledgeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocomotiveKnowledge(@PathVariable Long id) {
        locomotiveKnowledgeService.deleteLocomotiveKnowledge(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/document")
    public ResponseEntity<String> uploadDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String fileType) {
        return ResponseEntity.ok(locomotiveKnowledgeService.uploadDocument(id, file, fileType));
    }

    @DeleteMapping("/{id}/document")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id,
            @RequestParam("type") String fileType) {
        locomotiveKnowledgeService.deleteDocument(id, fileType);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validity/expired")
    public ResponseEntity<List<LocomotiveKnowledge>> getExpiredKnowledge() {
        return ResponseEntity.ok(locomotiveKnowledgeService.getExpiredKnowledge());
    }

    @GetMapping("/validity/needing-reminder")
    public ResponseEntity<List<LocomotiveKnowledge>> getKnowledgeNeedingReminder() {
        return ResponseEntity.ok(locomotiveKnowledgeService.getKnowledgeNeedingReminder());
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<LocomotiveKnowledge> renewKnowledge(@PathVariable Long id) {
        return ResponseEntity.ok(locomotiveKnowledgeService.renewKnowledge(id));
    }

    @PostMapping("/send-reminders")
    public ResponseEntity<Void> sendReminderEmails() {
        locomotiveKnowledgeService.sendReminderEmails();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validity/{status}")
    public ResponseEntity<List<LocomotiveKnowledge>> getKnowledgeByValidityStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(locomotiveKnowledgeService.getKnowledgeByValidityStatus(status));
    }
} 