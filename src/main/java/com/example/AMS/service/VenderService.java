package com.example.AMS.service;

import com.example.AMS.model.Vender;
import com.example.AMS.repository.VenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenderService {

    @Autowired
    private VenderRepository venderRepository;

    // Get all venders
    public List<Vender> getAllVenders() {
        return venderRepository.findAll();
    }

    // Save or update a vender
    public Vender saveVender(Vender vender) {
        return venderRepository.save(vender);
    }

    // Get vender by ID
    public Optional<Vender> getVenderById(String venderId) {
        return venderRepository.findById(venderId);
    }

    // Delete vender by ID
    public void deleteVenderById(String venderId) {
        venderRepository.deleteById(venderId);
    }
}
