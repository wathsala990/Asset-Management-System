package com.example.AMS.service;

import com.example.AMS.model.Vender;
import com.example.AMS.repository.VenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenderService {

    @Autowired
    private VenderRepository venderRepository;

    public List<Vender> getAllVenders() {
        return venderRepository.findAll();
    }
}
