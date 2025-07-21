package com.example.AMS.service;

import com.example.AMS.model.Location;
import com.example.AMS.repository.H_LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class H_LocationService {

    private final H_LocationRepository locationRepository;

    @Autowired
    public H_LocationService(H_LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Get location by ID
    public Optional<Location> getLocationById(String locationId) {
        return locationRepository.findById(locationId);
    }

    // Save or update location
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    // Delete location by ID
    public void deleteLocation(String locationId) {
        locationRepository.deleteById(locationId);
    }
}
