package com.connecteam.repository;

import com.connecteam.model.RailwayInstallation;
import com.connecteam.model.RailwayInstallation.InstallationType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RailwayInstallationRepository extends JpaRepository<RailwayInstallation, Long> {
    List<RailwayInstallation> findByType(InstallationType type);
    List<RailwayInstallation> findByZone(String zone);
    List<RailwayInstallation> findByCity(String city);
    List<RailwayInstallation> findByIsActive(boolean isActive);
    Optional<RailwayInstallation> findByNameAndType(String name, InstallationType type);
    List<RailwayInstallation> findByNameContainingIgnoreCase(String name);
} 