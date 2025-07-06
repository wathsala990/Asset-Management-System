package com.example.AMS.repository;

import com.example.AMS.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface M_LocationRepository extends JpaRepository<Location, String> {
    // Additional custom query methods (if needed) can be declared here
}
