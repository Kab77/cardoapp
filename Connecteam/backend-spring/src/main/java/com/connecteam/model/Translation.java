package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value", nullable = false, columnDefinition = "TEXT")
    private String value;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "category", nullable = false)
    private String category; // UI, EMAIL, ERROR, etc.
} 