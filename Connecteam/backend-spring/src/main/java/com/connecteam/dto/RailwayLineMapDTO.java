package com.connecteam.dto;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class RailwayLineMapDTO {
    private Long id;
    private String name;
    private String startPoint;
    private String endPoint;
    private List<Coordinate> coordinates = new ArrayList<>();
    private String status; // "valid", "expiring_soon", "expired"
    private String color; // Pour différencier les lignes sur la carte

    @Data
    public static class Coordinate {
        private Double latitude;
        private Double longitude;
    }
} 