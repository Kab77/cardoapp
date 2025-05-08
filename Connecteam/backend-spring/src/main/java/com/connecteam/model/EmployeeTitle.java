package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EmployeeTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TitleType titleType;
    
    @ManyToOne
    private Employee employee;
    
    public enum TitleType {
        DIRECTEUR,
        CONDUCTEUR,
        HR,
        TEAM_LEADER
    }
} 