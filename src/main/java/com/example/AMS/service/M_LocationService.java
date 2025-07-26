package com.example.AMS.service;

import com.example.AMS.model.Location;
import com.example.AMS.repository.M_LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class M_LocationService {

    @Autowired
    private M_LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location getLocationById(String locationId) {
        return locationRepository.findById(locationId).orElse(null);
    }
}
