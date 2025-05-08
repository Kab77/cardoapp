package com.connecteam.service;

import com.connecteam.model.RailwayYard;
import com.connecteam.repository.RailwayYardRepository;
import com.connecteam.repository.RailwayInstallationRepository;
import com.connecteam.dto.RailwayYardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RailwayYardService {

    @Autowired
    private RailwayYardRepository railwayYardRepository;

    @Autowired
    private RailwayInstallationRepository installationRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public RailwayYard createYard(RailwayYardDTO dto, MultipartFile document) throws IOException {
        RailwayYard yard = new RailwayYard();
        updateYardFromDTO(yard, dto);
        
        if (document != null && !document.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + document.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(document.getInputStream(), filePath);
            
            yard.setDocumentPath(filePath.toString());
            yard.setDocumentName(document.getOriginalFilename());
            yard.setUploadDate(LocalDateTime.now());
        }
        
        return railwayYardRepository.save(yard);
    }

    public List<RailwayYard> getYardsByInstallation(Long installationId) {
        return railwayYardRepository.findByInstallationId(installationId);
    }

    public List<RailwayYard> getYardsByZone(String zone) {
        return railwayYardRepository.findByZone(zone);
    }

    public RailwayYard updateYard(Long id, RailwayYardDTO dto, MultipartFile document) throws IOException {
        RailwayYard existingYard = railwayYardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Faisceau non trouvé"));

        if (document != null && !document.isEmpty()) {
            if (existingYard.getDocumentPath() != null) {
                Files.deleteIfExists(Paths.get(existingYard.getDocumentPath()));
            }

            String fileName = UUID.randomUUID().toString() + "_" + document.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(document.getInputStream(), filePath);

            existingYard.setDocumentPath(filePath.toString());
            existingYard.setDocumentName(document.getOriginalFilename());
            existingYard.setUploadDate(LocalDateTime.now());
        }

        updateYardFromDTO(existingYard, dto);
        return railwayYardRepository.save(existingYard);
    }

    private void updateYardFromDTO(RailwayYard yard, RailwayYardDTO dto) {
        yard.setName(dto.getName());
        yard.setZone(dto.getZone());
        yard.setDescription(dto.getDescription());
        yard.setStreetAddress(dto.getStreetAddress());
        yard.setPostalCode(dto.getPostalCode());
        yard.setCity(dto.getCity());
        yard.setAccessInstructions(dto.getAccessInstructions());
        yard.setParkingInstructions(dto.getParkingInstructions());
        
        if (dto.getInstallationId() != null) {
            yard.setInstallation(installationRepository.findById(dto.getInstallationId())
                .orElseThrow(() -> new RuntimeException("Installation non trouvée")));
        }
    }

    public void deleteYard(Long id) throws IOException {
        RailwayYard yard = railwayYardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Faisceau non trouvé"));

        if (yard.getDocumentPath() != null) {
            Files.deleteIfExists(Paths.get(yard.getDocumentPath()));
        }

        railwayYardRepository.delete(yard);
    }

    public RailwayYard getYardById(Long id) {
        return railwayYardRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Faisceau non trouvé"));
    }
} 