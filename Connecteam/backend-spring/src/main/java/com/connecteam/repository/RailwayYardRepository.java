package com.connecteam.repository;

import com.connecteam.model.RailwayYard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RailwayYardRepository extends JpaRepository<RailwayYard, Long> {
    List<RailwayYard> findByInstallationId(Long installationId);
    List<RailwayYard> findByZone(String zone);
} 