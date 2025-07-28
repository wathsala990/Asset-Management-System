package com.example.AMS.service;

import com.example.AMS.model.Location;
import com.example.AMS.repository.M_LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Location getLocationById(Long id) {
        Optional<Location> optional = locationRepository.findById(id);
        return optional.orElse(null);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
