package com.connecteam.controller;

import com.connecteam.model.RailwayYard;
import com.connecteam.service.RailwayYardService;
import com.connecteam.dto.RailwayYardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/railway-yards")
public class RailwayYardController {

    @Autowired
    private RailwayYardService railwayYardService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RailwayYard> createYard(
            @Valid @RequestPart("yard") RailwayYardDTO yard,
            @RequestPart(value = "document", required = false) MultipartFile document) throws IOException {
        return ResponseEntity.ok(railwayYardService.createYard(yard, document));
    }

    @GetMapping("/installation/{installationId}")
    public ResponseEntity<List<RailwayYard>> getYardsByInstallation(@PathVariable Long installationId) {
        return ResponseEntity.ok(railwayYardService.getYardsByInstallation(installationId));
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<RailwayYard>> getYardsByZone(@PathVariable String zone) {
        return ResponseEntity.ok(railwayYardService.getYardsByZone(zone));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RailwayYard> updateYard(
            @PathVariable Long id,
            @Valid @RequestPart("yard") RailwayYardDTO yard,
            @RequestPart(value = "document", required = false) MultipartFile document) throws IOException {
        return ResponseEntity.ok(railwayYardService.updateYard(id, yard, document));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteYard(@PathVariable Long id) throws IOException {
        railwayYardService.deleteYard(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<Resource> getDocument(@PathVariable Long id) throws IOException {
        RailwayYard yard = railwayYardService.getYardById(id);
        if (yard.getDocumentPath() == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(yard.getDocumentPath());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + yard.getDocumentName() + "\"")
                .body(resource);
    }
} 