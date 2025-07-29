// M_RoomService.java
package com.example.AMS.service;

import com.example.AMS.model.Room;
import com.example.AMS.repository.M_RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class M_RoomService {

    private final M_RoomRepository roomRepository;

    @Autowired
    public M_RoomService(M_RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(String roomId) {
        return roomRepository.findById(roomId);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }

    public List<Room> getRoomsByLocationId(String locationId) {
        return roomRepository.findByLocation_LocationId(locationId);
    }
}

