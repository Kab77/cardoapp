package com.connecteam.service;

import com.connecteam.model.*;
import com.connecteam.repository.*;
import com.connecteam.dto.DailyReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private EmailService emailService;

    private final Path fileStorageLocation = Paths.get("uploads/reports");

    public DailyReport createDailyReport(DailyReportDTO dto) {
        validateDailyReport(dto);
        
        DailyReport report = new DailyReport();
        updateReportFromDTO(report, dto);
        
        return dailyReportRepository.save(report);
    }

    public DailyReport updateDailyReport(Long id, DailyReportDTO dto) {
        DailyReport report = dailyReportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rapport non trouvé"));
            
        validateDailyReport(dto);
        updateReportFromDTO(report, dto);
        
        return dailyReportRepository.save(report);
    }

    private void validateDailyReport(DailyReportDTO dto) {
        // Vérifier que l'employé existe
        if (!employeeRepository.existsById(dto.getEmployeeId())) {
            throw new RuntimeException("Employé non trouvé");
        }

        // Vérifier que le planning existe
        if (!planningRepository.existsById(dto.getPlanningId())) {
            throw new RuntimeException("Planning non trouvé");
        }

        // Vérifier que les heures réelles sont cohérentes
        if (dto.getActualStartTime() != null && dto.getActualEndTime() != null) {
            if (dto.getActualEndTime().isBefore(dto.getActualStartTime())) {
                throw new RuntimeException("L'heure de fin réelle doit être après l'heure de début");
            }
        }
    }

    private void updateReportFromDTO(DailyReport report, DailyReportDTO dto) {
        report.setEmployee(employeeRepository.findById(dto.getEmployeeId())
            .orElseThrow(() -> new RuntimeException("Employé non trouvé")));
            
        report.setPlanning(planningRepository.findById(dto.getPlanningId())
            .orElseThrow(() -> new RuntimeException("Planning non trouvé")));
            
        report.setReportDate(dto.getReportDate());
        report.setPlannedStartTime(dto.getPlannedStartTime());
        report.setPlannedEndTime(dto.getPlannedEndTime());
        report.setActualStartTime(dto.getActualStartTime());
        report.setActualEndTime(dto.getActualEndTime());
        report.setEmployeeRemarks(dto.getEmployeeRemarks());
        report.setOtherRemarks(dto.getOtherRemarks());
    }

    public ReportAttachment addAttachment(Long reportId, MultipartFile file, boolean isEmployeeAttachment) {
        DailyReport report = dailyReportRepository.findById(reportId)
            .orElseThrow(() -> new RuntimeException("Rapport non trouvé"));

        try {
            // Créer le dossier s'il n'existe pas
            Files.createDirectories(fileStorageLocation);

            // Générer un nom de fichier unique
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = fileStorageLocation.resolve(fileName);

            // Sauvegarder le fichier
            Files.copy(file.getInputStream(), targetLocation);

            // Créer l'attachment
            ReportAttachment attachment = new ReportAttachment();
            attachment.setDailyReport(report);
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setFileSize(file.getSize());
            attachment.setFilePath(targetLocation.toString());
            attachment.setEmployeeAttachment(isEmployeeAttachment);

            return attachment;
        } catch (IOException ex) {
            throw new RuntimeException("Erreur lors de la sauvegarde du fichier", ex);
        }
    }

    public List<DailyReport> getEmployeeReports(Long employeeId, LocalDate date) {
        return dailyReportRepository.findByEmployeeIdAndReportDate(employeeId, date);
    }

    public List<DailyReport> getEmployeeReportsByDateRange(
            Long employeeId, LocalDate startDate, LocalDate endDate) {
        return dailyReportRepository.findByEmployeeIdAndReportDateBetween(
            employeeId, startDate, endDate);
    }

    public void updateReportStatus(Long id, ReportStatus status) {
        DailyReport report = dailyReportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rapport non trouvé"));
            
        report.setStatus(status);
        dailyReportRepository.save(report);

        // Envoyer un email si le rapport est validé
        if (status == ReportStatus.VALIDATED) {
            emailService.sendReportEmail(report);
        }
    }

    public Integer getEmployeeTotalHours(Long employeeId, LocalDate date) {
        return dailyReportRepository.getTotalActualHoursByEmployeeAndDate(employeeId, date);
    }
} 