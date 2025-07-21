package com.example.AMS.service;

import com.example.AMS.model.Room;
import com.example.AMS.repository.H_RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class H_RoomService {

    private final H_RoomRepository roomRepository;

    @Autowired
    public H_RoomService(H_RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get room by ID
    public Optional<Room> getRoomById(String roomId) {
        return roomRepository.findById(roomId);
    }

    // Save or update a room
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    // Delete room by ID
    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }
}
