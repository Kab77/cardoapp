package com.connecteam.repository;

import com.connecteam.model.LocomotiveKnowledge;
import com.connecteam.model.Client;
import com.connecteam.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LocomotiveKnowledgeRepository extends JpaRepository<LocomotiveKnowledge, Long> {
    List<LocomotiveKnowledge> findByClient(Client client);
    List<LocomotiveKnowledge> findByLocomotiveType(String locomotiveType);
    List<LocomotiveKnowledge> findByQualifiedEmployeesContaining(Employee employee);
    Optional<LocomotiveKnowledge> findByLocomotiveTypeAndLocomotiveNumber(String type, String number);
    List<LocomotiveKnowledge> findByIsActive(boolean isActive);
    List<LocomotiveKnowledge> findByClientAndIsActive(Client client, boolean isActive);
} 