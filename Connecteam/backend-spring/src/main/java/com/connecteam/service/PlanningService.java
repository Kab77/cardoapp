package com.connecteam.service;

import com.connecteam.model.*;
import com.connecteam.repository.*;
import com.connecteam.dto.PlanningDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private RailwayYardRepository railwayYardRepository;

    @Autowired
    private LocomotiveRepository locomotiveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Planning createPlanning(PlanningDTO dto) {
        validatePlanning(dto);
        
        Planning planning = new Planning();
        updatePlanningFromDTO(planning, dto);
        
        return planningRepository.save(planning);
    }

    public Planning updatePlanning(Long id, PlanningDTO dto) {
        Planning planning = planningRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Planning non trouvé"));
            
        validatePlanning(dto);
        updatePlanningFromDTO(planning, dto);
        
        return planningRepository.save(planning);
    }

    private void validatePlanning(PlanningDTO dto) {
        // Vérifier la durée minimale selon le type de service
        Service service = serviceRepository.findById(dto.getServiceId())
            .orElseThrow(() -> new RuntimeException("Service non trouvé"));
            
        Duration duration = Duration.between(dto.getStartTime(), dto.getEndTime());
        int hours = (int) duration.toHours();
        
        if (hours < service.getMinDurationHours()) {
            throw new RuntimeException(
                String.format("La durée minimale pour ce service est de %d heures", 
                service.getMinDurationHours()));
        }

        // Vérifier les disponibilités
        checkAvailability(dto);
    }

    private void checkAvailability(PlanningDTO dto) {
        // Vérifier la disponibilité de l'employé
        if (dto.getEmployeeId() != null) {
            List<Planning> employeePlannings = planningRepository
                .findByEmployeeIdAndWeekNumberAndYear(
                    dto.getEmployeeId(),
                    dto.getStartDate().getDayOfWeek().getValue(),
                    dto.getStartDate().getYear()
                );
                
            for (Planning p : employeePlannings) {
                if (isTimeOverlap(dto.getStartTime(), dto.getEndTime(), 
                                p.getStartTime(), p.getEndTime())) {
                    throw new RuntimeException("L'employé a déjà un service planifié sur ce créneau");
                }
            }
        }

        // Vérifier la disponibilité de la locomotive
        if (dto.getLocomotiveId() != null) {
            List<Planning> locomotivePlannings = planningRepository
                .findByLocomotiveIdAndWeekNumberAndYear(
                    dto.getLocomotiveId(),
                    dto.getStartDate().getDayOfWeek().getValue(),
                    dto.getStartDate().getYear()
                );
                
            for (Planning p : locomotivePlannings) {
                if (isTimeOverlap(dto.getStartTime(), dto.getEndTime(), 
                                p.getStartTime(), p.getEndTime())) {
                    throw new RuntimeException("La locomotive est déjà utilisée sur ce créneau");
                }
            }
        }
    }

    private boolean isTimeOverlap(LocalTime start1, LocalTime end1, 
                                LocalTime start2, LocalTime end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    private void updatePlanningFromDTO(Planning planning, PlanningDTO dto) {
        planning.setClient(clientRepository.findById(dto.getClientId())
            .orElseThrow(() -> new RuntimeException("Client non trouvé")));
            
        planning.setService(serviceRepository.findById(dto.getServiceId())
            .orElseThrow(() -> new RuntimeException("Service non trouvé")));
            
        planning.setStartDate(dto.getStartDate());
        planning.setEndDate(dto.getEndDate());
        planning.setStartTime(dto.getStartTime());
        planning.setEndTime(dto.getEndTime());
        
        if (dto.getRailwayYardId() != null) {
            planning.setRailwayYard(railwayYardRepository.findById(dto.getRailwayYardId())
                .orElseThrow(() -> new RuntimeException("Faisceau non trouvé")));
        }
        
        if (dto.getLocomotiveId() != null) {
            planning.setLocomotive(locomotiveRepository.findById(dto.getLocomotiveId())
                .orElseThrow(() -> new RuntimeException("Locomotive non trouvée")));
        }
        
        if (dto.getEmployeeId() != null) {
            planning.setEmployee(employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé")));
        }
        
        planning.setRemarks(dto.getRemarks());
    }

    public List<Planning> getClientPlanning(Long clientId, Integer weekNumber, Integer year) {
        return planningRepository.findByClientIdAndWeekNumberAndYear(clientId, weekNumber, year);
    }

    public List<Planning> getEmployeePlanning(Long employeeId, Integer weekNumber, Integer year) {
        return planningRepository.findByEmployeeIdAndWeekNumberAndYear(employeeId, weekNumber, year);
    }

    public Integer getClientTotalHours(Long clientId, Integer weekNumber, Integer year) {
        return planningRepository.getTotalHoursByClientAndWeek(clientId, weekNumber, year);
    }

    public void deletePlanning(Long id) {
        if (!planningRepository.existsById(id)) {
            throw new RuntimeException("Planning non trouvé");
        }
        planningRepository.deleteById(id);
    }
} 