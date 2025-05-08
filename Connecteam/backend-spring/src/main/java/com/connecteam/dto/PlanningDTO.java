package com.connecteam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PlanningDTO {
    private Long id;

    @NotNull(message = "Le client est obligatoire")
    private Long clientId;

    @NotNull(message = "Le service est obligatoire")
    private Long serviceId;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate startDate;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate endDate;

    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime startTime;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime endTime;

    private Long railwayYardId;
    private Long locomotiveId;
    private Long employeeId;
    private String remarks;
} 