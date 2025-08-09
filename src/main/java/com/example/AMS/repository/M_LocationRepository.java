package com.example.AMS.repository;

import com.example.AMS.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface M_LocationRepository extends JpaRepository<Location, String> {
    List<Location> findByDeletedFalse();
}