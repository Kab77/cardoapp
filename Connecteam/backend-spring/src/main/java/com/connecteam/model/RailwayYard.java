package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class RailwayYard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String zone;
    private String description;
    
    @Column(name = "street_address")
    private String streetAddress;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    private String city;
    
    @Column(name = "access_instructions", length = 1000)
    private String accessInstructions;
    
    @Column(name = "parking_instructions", length = 1000)
    private String parkingInstructions;
    
    @Column(name = "document_path")
    private String documentPath;
    
    @Column(name = "document_name")
    private String documentName;
    
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
    
    @ManyToOne
    private RailwayInstallation installation;
} 