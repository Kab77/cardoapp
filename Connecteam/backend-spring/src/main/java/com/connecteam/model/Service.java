package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // VISITE, ESSAI_FREIN, MANOEUVRE, CONDUITE

    @Column(name = "min_duration_hours")
    private Integer minDurationHours;

    @Column(name = "color_code")
    private String colorCode; // Code couleur pour le planning
} 