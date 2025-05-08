package com.connecteam.service;

import com.connecteam.model.LocomotiveKnowledge;
import com.connecteam.model.Client;
import com.connecteam.model.Employee;
import com.connecteam.repository.LocomotiveKnowledgeRepository;
import com.connecteam.repository.ClientRepository;
import com.connecteam.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocomotiveKnowledgeService {

    @Autowired
    private LocomotiveKnowledgeRepository locomotiveKnowledgeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    private final Path fileStorageLocation = Paths.get("uploads/locomotives");

    public LocomotiveKnowledge createLocomotiveKnowledge(LocomotiveKnowledge knowledge) {
        return locomotiveKnowledgeRepository.save(knowledge);
    }

    public LocomotiveKnowledge updateLocomotiveKnowledge(Long id, LocomotiveKnowledge knowledge) {
        LocomotiveKnowledge existingKnowledge = locomotiveKnowledgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Connaissance de locomotive non trouvée"));

        existingKnowledge.setLocomotiveType(knowledge.getLocomotiveType());
        existingKnowledge.setLocomotiveNumber(knowledge.getLocomotiveNumber());
        existingKnowledge.setClient(knowledge.getClient());
        existingKnowledge.setDescription(knowledge.getDescription());
        existingKnowledge.setTechnicalSpecs(knowledge.getTechnicalSpecs());
        existingKnowledge.setSafetyInstructions(knowledge.getSafetyInstructions());
        existingKnowledge.setActive(knowledge.isActive());

        return locomotiveKnowledgeRepository.save(existingKnowledge);
    }

    public List<LocomotiveKnowledge> getAllLocomotiveKnowledge() {
        return locomotiveKnowledgeRepository.findAll();
    }

    public List<LocomotiveKnowledge> getLocomotiveKnowledgeByClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return locomotiveKnowledgeRepository.findByClient(client);
    }

    public List<LocomotiveKnowledge> getLocomotiveKnowledgeByType(String type) {
        return locomotiveKnowledgeRepository.findByLocomotiveType(type);
    }

    public List<LocomotiveKnowledge> getLocomotiveKnowledgeByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        return locomotiveKnowledgeRepository.findByQualifiedEmployeesContaining(employee);
    }

    public LocomotiveKnowledge getLocomotiveKnowledgeById(Long id) {
        return locomotiveKnowledgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Connaissance de locomotive non trouvée"));
    }

    public void deleteLocomotiveKnowledge(Long id) {
        LocomotiveKnowledge knowledge = locomotiveKnowledgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Connaissance de locomotive non trouvée"));

        // Supprimer les fichiers associés
        deleteAssociatedFiles(knowledge);
        
        locomotiveKnowledgeRepository.deleteById(id);
    }

    public String uploadDocument(Long id, MultipartFile file, String fileType) {
        LocomotiveKnowledge knowledge = locomotiveKnowledgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Connaissance de locomotive non trouvée"));

        try {
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path targetLocation = fileStorageLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation);

            // Mettre à jour le chemin approprié selon le type de fichier
            switch (fileType.toLowerCase()) {
                case "document":
                    knowledge.setDocumentPath(fileName);
                    break;
                case "image":
                    knowledge.setImagePath(fileName);
                    break;
                case "text":
                    knowledge.setTextFilePath(fileName);
                    break;
                default:
                    throw new RuntimeException("Type de fichier non supporté");
            }

            locomotiveKnowledgeRepository.save(knowledge);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
        }
    }

    public void deleteDocument(Long id, String fileType) {
        LocomotiveKnowledge knowledge = locomotiveKnowledgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Connaissance de locomotive non trouvée"));

        String filePath = null;
        switch (fileType.toLowerCase()) {
            case "document":
                filePath = knowledge.getDocumentPath();
                knowledge.setDocumentPath(null);
                break;
            case "image":
                filePath = knowledge.getImagePath();
                knowledge.setImagePath(null);
                break;
            case "text":
                filePath = knowledge.getTextFilePath();
                knowledge.setTextFilePath(null);
                break;
            default:
                throw new RuntimeException("Type de fichier non supporté");
        }

        if (filePath != null) {
            try {
                Files.deleteIfExists(fileStorageLocation.resolve(filePath));
                locomotiveKnowledgeRepository.save(knowledge);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la suppression du fichier", e);
            }
        }
    }

    private void deleteAssociatedFiles(LocomotiveKnowledge knowledge) {
        try {
            if (knowledge.getDocumentPath() != null) {
                Files.deleteIfExists(fileStorageLocation.resolve(knowledge.getDocumentPath()));
            }
            if (knowledge.getImagePath() != null) {
                Files.deleteIfExists(fileStorageLocation.resolve(knowledge.getImagePath()));
            }
            if (knowledge.getTextFilePath() != null) {
                Files.deleteIfExists(fileStorageLocation.resolve(knowledge.getTextFilePath()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression des fichiers", e);
        }
    }

    public List<LocomotiveKnowledge> getExpiredKnowledge() {
        return locomotiveKnowledgeRepository.findAll().stream()
            .filter(LocomotiveKnowledge::isExpired)
            .collect(Collectors.toList());
    }

    public List<LocomotiveKnowledge> getKnowledgeNeedingReminder() {
        return locomotiveKnowledgeRepository.findAll().stream()
            .filter(LocomotiveKnowledge::needsReminder)
            .collect(Collectors.toList());
    }

    public LocomotiveKnowledge renewKnowledge(Long id) {
        LocomotiveKnowledge knowledge = locomotiveKnowledgeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Connaissance de locomotive non trouvée"));
        
        knowledge.renewValidity();
        return locomotiveKnowledgeRepository.save(knowledge);
    }

    public void sendReminderEmails() {
        List<LocomotiveKnowledge> knowledgeNeedingReminder = getKnowledgeNeedingReminder();
        
        for (LocomotiveKnowledge knowledge : knowledgeNeedingReminder) {
            for (Employee employee : knowledge.getQualifiedEmployees()) {
                String subject = "Rappel : Renouvellement de connaissance de locomotive";
                String message = String.format(
                    "Cher %s,\n\n" +
                    "Votre connaissance de la locomotive %s %s expire dans 6 mois (le %s).\n" +
                    "Veuillez planifier le renouvellement de votre qualification.\n\n" +
                    "Cordialement,\n" +
                    "L'équipe Connecteam",
                    employee.getFirstName(),
                    knowledge.getLocomotiveType(),
                    knowledge.getLocomotiveNumber(),
                    knowledge.getValidityEndDate().toLocalDate()
                );
                
                emailService.sendEmail(employee.getEmail(), subject, message);
            }
            
            knowledge.setReminderSent(true);
            locomotiveKnowledgeRepository.save(knowledge);
        }
    }

    public List<LocomotiveKnowledge> getKnowledgeByValidityStatus(String status) {
        List<LocomotiveKnowledge> allKnowledge = locomotiveKnowledgeRepository.findAll();
        
        switch (status.toLowerCase()) {
            case "valid":
                return allKnowledge.stream()
                    .filter(k -> !k.isExpired())
                    .collect(Collectors.toList());
            case "expired":
                return allKnowledge.stream()
                    .filter(LocomotiveKnowledge::isExpired)
                    .collect(Collectors.toList());
            case "expiring_soon":
                return allKnowledge.stream()
                    .filter(k -> !k.isExpired() && k.needsReminder())
                    .collect(Collectors.toList());
            default:
                throw new RuntimeException("Statut de validité non reconnu");
        }
    }
} 