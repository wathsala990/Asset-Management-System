package com.example.AMS.repository;

import com.example.AMS.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface M_RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomName(String roomName);
    List<Room> findByLocationLocationId(String locationId); // Correct method name
}