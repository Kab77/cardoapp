package com.connecteam.controller;

import com.connecteam.model.RailwayLine;
import com.connecteam.service.RailwayLineService;
import com.connecteam.dto.RailwayLineMapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/railway-lines")
public class RailwayLineController {

    @Autowired
    private RailwayLineService railwayLineService;

    @PostMapping
    public ResponseEntity<RailwayLine> createLine(@RequestBody RailwayLine line) {
        return ResponseEntity.ok(railwayLineService.createLine(line));
    }

    @GetMapping("/needing-renewal")
    public ResponseEntity<List<RailwayLine>> getLinesNeedingRenewal() {
        return ResponseEntity.ok(railwayLineService.getLinesNeedingRenewal());
    }

    @GetMapping("/expired")
    public ResponseEntity<List<RailwayLine>> getExpiredLines() {
        return ResponseEntity.ok(railwayLineService.getExpiredLines());
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<RailwayLine> renewLine(@PathVariable Long id) {
        return ResponseEntity.ok(railwayLineService.renewLine(id));
    }

    @GetMapping("/map")
    public ResponseEntity<List<RailwayLineMapDTO>> getLinesForMap() {
        return ResponseEntity.ok(railwayLineService.getAllLinesForMap());
    }
} 