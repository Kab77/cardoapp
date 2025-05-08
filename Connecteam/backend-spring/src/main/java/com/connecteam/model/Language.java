package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code; // fr, nl

    @Column(name = "name", nullable = false)
    private String name; // Français, Nederlands

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;
} 