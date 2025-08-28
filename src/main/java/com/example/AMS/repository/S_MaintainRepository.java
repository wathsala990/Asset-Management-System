package com.example.AMS.repository;

import com.example.AMS.model.Maintain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S_MaintainRepository extends JpaRepository<Maintain, String> {
}
