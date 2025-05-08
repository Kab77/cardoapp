package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Locomotive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificationNumber;
    private String type;
    private String capabilities;
    private String maintenanceStatus;
    private LocalDateTime lastMaintenanceDate;
    private String status;
    
    @ManyToOne
    private RailwayInstallation currentLocation;
} 