package com.connecteam.repository;

import com.connecteam.model.DailyReport;
import com.connecteam.model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    List<DailyReport> findByEmployeeIdAndReportDate(Long employeeId, LocalDate reportDate);
    
    List<DailyReport> findByEmployeeIdAndReportDateBetween(
        Long employeeId, LocalDate startDate, LocalDate endDate);
    
    List<DailyReport> findByPlanningId(Long planningId);
    
    List<DailyReport> findByStatus(ReportStatus status);
    
    @Query("SELECT d FROM DailyReport d WHERE d.employee.id = ?1 AND d.reportDate = ?2 AND d.status = ?3")
    List<DailyReport> findByEmployeeIdAndReportDateAndStatus(
        Long employeeId, LocalDate reportDate, ReportStatus status);
    
    @Query("SELECT SUM(TIMESTAMPDIFF(HOUR, d.actualStartTime, d.actualEndTime)) " +
           "FROM DailyReport d WHERE d.employee.id = ?1 AND d.reportDate = ?2")
    Integer getTotalActualHoursByEmployeeAndDate(Long employeeId, LocalDate reportDate);
} 