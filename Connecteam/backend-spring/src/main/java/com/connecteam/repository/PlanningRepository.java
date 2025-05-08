package com.connecteam.repository;

import com.connecteam.model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
    List<Planning> findByClientIdAndWeekNumberAndYear(Long clientId, Integer weekNumber, Integer year);
    
    List<Planning> findByEmployeeIdAndWeekNumberAndYear(Long employeeId, Integer weekNumber, Integer year);
    
    List<Planning> findByRailwayYardIdAndWeekNumberAndYear(Long railwayYardId, Integer weekNumber, Integer year);
    
    List<Planning> findByLocomotiveIdAndWeekNumberAndYear(Long locomotiveId, Integer weekNumber, Integer year);
    
    @Query("SELECT p FROM Planning p WHERE p.startDate >= ?1 AND p.endDate <= ?2")
    List<Planning> findByDateRange(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(TIMESTAMPDIFF(HOUR, p.startTime, p.endTime)) FROM Planning p " +
           "WHERE p.clientId = ?1 AND p.weekNumber = ?2 AND p.year = ?3")
    Integer getTotalHoursByClientAndWeek(Long clientId, Integer weekNumber, Integer year);
} 