package com.connecteam.controller;

import com.connecteam.model.Planning;
import com.connecteam.service.PlanningService;
import com.connecteam.dto.PlanningDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    @PostMapping
    public ResponseEntity<Planning> createPlanning(@Valid @RequestBody PlanningDTO planningDTO) {
        return ResponseEntity.ok(planningService.createPlanning(planningDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planning> updatePlanning(
            @PathVariable Long id,
            @Valid @RequestBody PlanningDTO planningDTO) {
        return ResponseEntity.ok(planningService.updatePlanning(id, planningDTO));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Planning>> getClientPlanning(
            @PathVariable Long clientId,
            @RequestParam Integer weekNumber,
            @RequestParam Integer year) {
        return ResponseEntity.ok(planningService.getClientPlanning(clientId, weekNumber, year));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Planning>> getEmployeePlanning(
            @PathVariable Long employeeId,
            @RequestParam Integer weekNumber,
            @RequestParam Integer year) {
        return ResponseEntity.ok(planningService.getEmployeePlanning(employeeId, weekNumber, year));
    }

    @GetMapping("/client/{clientId}/hours")
    public ResponseEntity<Integer> getClientTotalHours(
            @PathVariable Long clientId,
            @RequestParam Integer weekNumber,
            @RequestParam Integer year) {
        return ResponseEntity.ok(planningService.getClientTotalHours(clientId, weekNumber, year));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id) {
        planningService.deletePlanning(id);
        return ResponseEntity.ok().build();
    }
} 