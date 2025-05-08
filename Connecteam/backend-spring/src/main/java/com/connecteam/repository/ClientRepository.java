package com.connecteam.repository;

import com.connecteam.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findBySiret(String siret);
    List<Client> findByNameContainingIgnoreCase(String name);
    List<Client> findByCity(String city);
    boolean existsBySiret(String siret);
} 