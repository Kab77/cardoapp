package com.connecteam.service;

import com.connecteam.model.RailwayLine;
import com.connecteam.repository.RailwayLineRepository;
import com.connecteam.dto.RailwayLineMapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RailwayLineService {

    @Autowired
    private RailwayLineRepository railwayLineRepository;

    public RailwayLine createLine(RailwayLine line) {
        // Définir la date d'expiration à 1 an après la date de validation
        line.setExpirationDate(line.getValidationDate().plusYears(1));
        return railwayLineRepository.save(line);
    }

    public List<RailwayLine> getLinesNeedingRenewal() {
        LocalDate sixMonthsFromNow = LocalDate.now().plusMonths(6);
        return railwayLineRepository.findAll().stream()
            .filter(line -> line.getExpirationDate().isBefore(sixMonthsFromNow))
            .collect(Collectors.toList());
    }

    public List<RailwayLine> getExpiredLines() {
        return railwayLineRepository.findAll().stream()
            .filter(line -> line.getExpirationDate().isBefore(LocalDate.now()))
            .collect(Collectors.toList());
    }

    public RailwayLine renewLine(Long lineId) {
        RailwayLine line = railwayLineRepository.findById(lineId)
            .orElseThrow(() -> new RuntimeException("Ligne non trouvée"));
        
        line.setValidationDate(LocalDate.now());
        line.setExpirationDate(LocalDate.now().plusYears(1));
        
        return railwayLineRepository.save(line);
    }

    public List<RailwayLineMapDTO> getAllLinesForMap() {
        LocalDate now = LocalDate.now();
        LocalDate sixMonthsFromNow = now.plusMonths(6);

        return railwayLineRepository.findAll().stream()
            .map(line -> {
                RailwayLineMapDTO dto = new RailwayLineMapDTO();
                dto.setId(line.getId());
                dto.setName(line.getName());
                dto.setStartPoint(line.getStartPoint());
                dto.setEndPoint(line.getEndPoint());
                dto.setCoordinates(line.getCoordinates().stream()
                    .map(coord -> {
                        RailwayLineMapDTO.Coordinate dtoCoord = new RailwayLineMapDTO.Coordinate();
                        dtoCoord.setLatitude(coord.getLatitude());
                        dtoCoord.setLongitude(coord.getLongitude());
                        return dtoCoord;
                    })
                    .collect(Collectors.toList()));

                // Définir le statut et la couleur
                if (line.getExpirationDate().isBefore(now)) {
                    dto.setStatus("expired");
                    dto.setColor("#FF0000"); // Rouge pour les lignes expirées
                } else if (line.getExpirationDate().isBefore(sixMonthsFromNow)) {
                    dto.setStatus("expiring_soon");
                    dto.setColor("#FFA500"); // Orange pour les lignes qui expirent bientôt
                } else {
                    dto.setStatus("valid");
                    dto.setColor("#00FF00"); // Vert pour les lignes valides
                }

                return dto;
            })
            .collect(Collectors.toList());
    }
} 