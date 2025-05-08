package com.connecteam.repository;

import com.connecteam.model.Locomotive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocomotiveRepository extends JpaRepository<Locomotive, Long> {
} 