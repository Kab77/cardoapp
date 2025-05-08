package com.connecteam.service;

import com.connecteam.model.RailwayInstallation;
import com.connecteam.model.RailwayInstallation.InstallationType;
import com.connecteam.repository.RailwayInstallationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class RailwayInstallationService {

    @Autowired
    private RailwayInstallationRepository installationRepository;

    private final Path fileStorageLocation = Paths.get("uploads/installations");

    public RailwayInstallation createInstallation(RailwayInstallation installation) {
        return installationRepository.save(installation);
    }

    public RailwayInstallation updateInstallation(Long id, RailwayInstallation installation) {
        RailwayInstallation existingInstallation = installationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Installation non trouvée"));

        // Mise à jour des propriétés
        existingInstallation.setName(installation.getName());
        existingInstallation.setType(installation.getType());
        existingInstallation.setZone(installation.getZone());
        existingInstallation.setLatitude(installation.getLatitude());
        existingInstallation.setLongitude(installation.getLongitude());
        existingInstallation.setAddress(installation.getAddress());
        existingInstallation.setPostalCode(installation.getPostalCode());
        existingInstallation.setCity(installation.getCity());
        existingInstallation.setCountry(installation.getCountry());
        existingInstallation.setContactName(installation.getContactName());
        existingInstallation.setContactEmail(installation.getContactEmail());
        existingInstallation.setContactPhone(installation.getContactPhone());
        existingInstallation.setActive(installation.isActive());

        return installationRepository.save(existingInstallation);
    }

    public List<RailwayInstallation> getAllInstallations() {
        return installationRepository.findAll();
    }

    public List<RailwayInstallation> getInstallationsByType(InstallationType type) {
        return installationRepository.findByType(type);
    }

    public List<RailwayInstallation> getInstallationsByZone(String zone) {
        return installationRepository.findByZone(zone);
    }

    public List<RailwayInstallation> getInstallationsByCity(String city) {
        return installationRepository.findByCity(city);
    }

    public List<RailwayInstallation> getActiveInstallations() {
        return installationRepository.findByIsActive(true);
    }

    public RailwayInstallation getInstallationById(Long id) {
        return installationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Installation non trouvée"));
    }

    public void deleteInstallation(Long id) {
        RailwayInstallation installation = installationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Installation non trouvée"));
        
        // Supprimer le document de service s'il existe
        if (installation.getServiceDocumentPath() != null) {
            try {
                Files.deleteIfExists(fileStorageLocation.resolve(installation.getServiceDocumentPath()));
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la suppression du document", e);
            }
        }
        
        installationRepository.deleteById(id);
    }

    public String uploadServiceDocument(Long id, MultipartFile file) {
        RailwayInstallation installation = installationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Installation non trouvée"));

        try {
            // Créer le répertoire s'il n'existe pas
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            // Générer un nom de fichier unique
            String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path targetLocation = fileStorageLocation.resolve(fileName);

            // Sauvegarder le fichier
            Files.copy(file.getInputStream(), targetLocation);

            // Mettre à jour le chemin du document
            installation.setServiceDocumentPath(fileName);
            installationRepository.save(installation);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du téléchargement du document", e);
        }
    }

    public void deleteServiceDocument(Long id) {
        RailwayInstallation installation = installationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Installation non trouvée"));

        if (installation.getServiceDocumentPath() != null) {
            try {
                Files.deleteIfExists(fileStorageLocation.resolve(installation.getServiceDocumentPath()));
                installation.setServiceDocumentPath(null);
                installationRepository.save(installation);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la suppression du document", e);
            }
        }
    }
} 