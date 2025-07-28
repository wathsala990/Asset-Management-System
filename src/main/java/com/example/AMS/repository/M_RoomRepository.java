package com.example.AMS.repository;

import com.example.AMS.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface M_RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findByRoomName(String roomName);
}
