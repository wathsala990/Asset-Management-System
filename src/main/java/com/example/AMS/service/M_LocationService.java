// M_LocationService.java
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

    public List<Location> getAllLocations() {
        return locationRepository.findByDeletedFalse();
    }

    public Location getLocationById(String locationId) {
        return locationRepository.findById(locationId).orElse(null);
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public void updateLocation(String locationId, Location location) {
        location.setLocationId(locationId);
        locationRepository.save(location);
    }

    public void deleteLocation(String locationId) {
        locationRepository.deleteById(locationId);
    }

    public void softDeleteLocation(String locationId) {
        Location location = getLocationById(locationId);
        if (location != null) {
            location.setDeleted(true);
            locationRepository.save(location);
        }
    }
}
