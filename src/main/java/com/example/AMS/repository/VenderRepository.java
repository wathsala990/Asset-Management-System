package com.example.AMS.repository;

import com.example.AMS.model.Vender;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.example.AMS.model.Vender;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VenderRepository extends JpaRepository<Vender, String> {
    List<Vender> findByVenderNameContainingIgnoreCase(String venderName);
    Optional<Vender> findByVenderNameAndContactNo(String venderName, int contactNo);
    Optional<Vender> findByVenderName(String venderName);
}
