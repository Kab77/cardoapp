package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class LocomotiveKnowledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "locomotive_type", nullable = false)
    private String locomotiveType; // Type de locomotive (ex: HLD 77, HLD 86, etc.)

    @Column(name = "locomotive_number", nullable = false)
    private String locomotiveNumber; // Numéro de la locomotive

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; // Client propriétaire de la locomotive

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "technical_specs", columnDefinition = "TEXT")
    private String technicalSpecs;

    @Column(name = "safety_instructions", columnDefinition = "TEXT")
    private String safetyInstructions;

    @Column(name = "document_path")
    private String documentPath; // Chemin vers le document PDF principal

    @Column(name = "image_path")
    private String imagePath; // Chemin vers l'image de la locomotive

    @Column(name = "text_file_path")
    private String textFilePath; // Chemin vers le fichier texte avec informations supplémentaires

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "validity_start_date", nullable = false)
    private LocalDateTime validityStartDate;

    @Column(name = "validity_end_date", nullable = false)
    private LocalDateTime validityEndDate;

    @Column(name = "reminder_sent", nullable = false)
    private boolean reminderSent = false;

    @ManyToMany
    @JoinTable(
        name = "employee_locomotive_knowledge",
        joinColumns = @JoinColumn(name = "locomotive_knowledge_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> qualifiedEmployees;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        validityStartDate = LocalDateTime.now();
        validityEndDate = validityStartDate.plusYears(3);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(validityEndDate);
    }

    public boolean needsReminder() {
        LocalDateTime reminderDate = validityEndDate.minusMonths(6);
        return !reminderSent && LocalDateTime.now().isAfter(reminderDate);
    }

    public void renewValidity() {
        validityStartDate = LocalDateTime.now();
        validityEndDate = validityStartDate.plusYears(3);
        reminderSent = false;
    }
} 