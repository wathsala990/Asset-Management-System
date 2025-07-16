package com.example.AMS.service;

import com.example.AMS.model.Location;
import com.example.AMS.repository.M_LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class M_LocationService {

    private final M_LocationRepository locationRepository;

    public M_LocationService(M_LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }


    public Optional<Location> getLocationById(String locationID) {
        return locationRepository.findById(locationID);
    }


    public void saveLocation(Location location) {
        locationRepository.save(location);
    }


    public void deleteLocation(String locationID) {
        locationRepository.deleteById(locationID);
    }
}
