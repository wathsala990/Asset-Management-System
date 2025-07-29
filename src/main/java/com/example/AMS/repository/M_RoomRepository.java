package com.example.AMS.repository;

import com.example.AMS.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface M_RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByLocation_LocationId(String locationId);
}