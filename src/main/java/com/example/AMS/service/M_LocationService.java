package com.example.AMS.service;

import com.example.AMS.model.Location;
import com.example.AMS.repository.M_LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class M_LocationService {

    private final M_LocationRepository locationRepository;

    @Autowired
    public M_LocationService(M_LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Get location by ID
    public Location getLocationById(Long locationId) {
        return locationRepository.findById(locationId).orElse(null);
    }

    // Save or update location
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    // Delete location
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    // Check if location exists
    public boolean locationExists(Long locationId) {
        return locationRepository.existsById(locationId);
    }
}