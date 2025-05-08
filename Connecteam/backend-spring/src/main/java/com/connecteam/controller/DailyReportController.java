package com.connecteam.controller;

import com.connecteam.model.DailyReport;
import com.connecteam.model.ReportAttachment;
import com.connecteam.model.ReportStatus;
import com.connecteam.service.DailyReportService;
import com.connecteam.dto.DailyReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class DailyReportController {

    @Autowired
    private DailyReportService dailyReportService;

    @PostMapping
    public ResponseEntity<DailyReport> createDailyReport(@Valid @RequestBody DailyReportDTO reportDTO) {
        return ResponseEntity.ok(dailyReportService.createDailyReport(reportDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyReport> updateDailyReport(
            @PathVariable Long id,
            @Valid @RequestBody DailyReportDTO reportDTO) {
        return ResponseEntity.ok(dailyReportService.updateDailyReport(id, reportDTO));
    }

    @PostMapping("/{id}/attachments")
    public ResponseEntity<ReportAttachment> addAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("isEmployeeAttachment") boolean isEmployeeAttachment) {
        return ResponseEntity.ok(dailyReportService.addAttachment(id, file, isEmployeeAttachment));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<DailyReport>> getEmployeeReports(
            @PathVariable Long employeeId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(dailyReportService.getEmployeeReports(employeeId, date));
    }

    @GetMapping("/employee/{employeeId}/range")
    public ResponseEntity<List<DailyReport>> getEmployeeReportsByDateRange(
            @PathVariable Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(dailyReportService.getEmployeeReportsByDateRange(
            employeeId, startDate, endDate));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateReportStatus(
            @PathVariable Long id,
            @RequestParam ReportStatus status) {
        dailyReportService.updateReportStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee/{employeeId}/hours")
    public ResponseEntity<Integer> getEmployeeTotalHours(
            @PathVariable Long employeeId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(dailyReportService.getEmployeeTotalHours(employeeId, date));
    }
} 