package com.connecteam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DailyReportDTO {
    private Long id;

    @NotNull(message = "L'employé est obligatoire")
    private Long employeeId;

    @NotNull(message = "Le planning est obligatoire")
    private Long planningId;

    @NotNull(message = "La date du rapport est obligatoire")
    private LocalDate reportDate;

    @NotNull(message = "L'heure de début prévue est obligatoire")
    private LocalTime plannedStartTime;

    @NotNull(message = "L'heure de fin prévue est obligatoire")
    private LocalTime plannedEndTime;

    private LocalTime actualStartTime;
    private LocalTime actualEndTime;
    private String employeeRemarks;
    private String otherRemarks;
} 