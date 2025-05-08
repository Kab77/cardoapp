package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class RailwayLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String startPoint;
    private String endPoint;
    private Double length;
    private String lineType;
    
    @Column(nullable = false)
    private LocalDate validationDate;
    
    @Column(nullable = false)
    private LocalDate expirationDate;
    
    @ManyToOne
    private RailwayInstallation installation;

    @ElementCollection
    @CollectionTable(name = "railway_line_coordinates")
    private List<Coordinate> coordinates = new ArrayList<>();

    @Embeddable
    @Data
    public static class Coordinate {
        private Double latitude;
        private Double longitude;
    }
} 