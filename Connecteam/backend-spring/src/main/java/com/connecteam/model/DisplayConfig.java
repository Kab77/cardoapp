package com.connecteam.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DisplayConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "theme", nullable = false)
    private String theme = "light"; // light, dark

    @Column(name = "primary_color", nullable = false)
    private String primaryColor = "#1976d2";

    @Column(name = "secondary_color", nullable = false)
    private String secondaryColor = "#dc004e";

    @Column(name = "font_size", nullable = false)
    private Integer fontSize = 14; // en pixels

    @Column(name = "compact_view", nullable = false)
    private boolean compactView = false;

    @Column(name = "show_weekends", nullable = false)
    private boolean showWeekends = true;

    @Column(name = "default_view", nullable = false)
    private String defaultView = "week"; // day, week, month

    @Column(name = "time_format", nullable = false)
    private String timeFormat = "24h"; // 12h, 24h

    @Column(name = "mobile_optimized", nullable = false)
    private boolean mobileOptimized = true;

    @Column(name = "show_employee_photo", nullable = false)
    private boolean showEmployeePhoto = true;

    @Column(name = "show_service_color", nullable = false)
    private boolean showServiceColor = true;

    @Column(name = "show_remarks_preview", nullable = false)
    private boolean showRemarksPreview = true;

    @Column(name = "max_visible_services", nullable = false)
    private Integer maxVisibleServices = 5;

    @Column(name = "refresh_interval", nullable = false)
    private Integer refreshInterval = 5; // en minutes

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;
} 