package com.example.AMS.service;

import com.example.AMS.model.Location;
import com.example.AMS.repository.M_LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Find a location by ID
    public Optional<Location> getLocationById(String locationId) {
        return locationRepository.findById(locationId);
    }

    // Save or update a location
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    // Delete a location
    public void deleteLocation(String locationId) {
        locationRepository.deleteById(locationId);
    }
}
