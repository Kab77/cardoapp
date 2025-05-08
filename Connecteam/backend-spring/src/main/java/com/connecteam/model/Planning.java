package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "railway_yard_id")
    private RailwayYard railwayYard;

    @ManyToOne
    @JoinColumn(name = "locomotive_id")
    private Locomotive locomotive;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Column(name = "year")
    private Integer year;

    @PrePersist
    protected void onPersist() {
        if (startDate != null) {
            weekNumber = startDate.getDayOfWeek().getValue();
            year = startDate.getYear();
        }
    }
} 