package com.example.AMS.repository;

import com.example.AMS.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface M_LocationRepository extends JpaRepository<Location, Long> {
    // You can add custom queries here if needed
}
