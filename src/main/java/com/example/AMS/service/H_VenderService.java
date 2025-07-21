package com.example.AMS.service;

import com.example.AMS.model.Vender;
import com.example.AMS.repository.H_VenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class H_VenderService {

    private final H_VenderRepository venderRepository;

    @Autowired
    public H_VenderService(H_VenderRepository venderRepository) {
        this.venderRepository = venderRepository;
    }

    // Get all vendors
    public List<Vender> getAllVenders() {
        return venderRepository.findAll();
    }

    // Get a vendor by ID
    public Optional<Vender> getVenderById(String venderId) {
        return venderRepository.findById(venderId);
    }

    // Save or update a vendor
    public void saveVender(Vender vender) {
        venderRepository.save(vender);
    }

    // Delete a vendor by ID
    public void deleteVender(String venderId) {
        venderRepository.deleteById(venderId);
    }
}
