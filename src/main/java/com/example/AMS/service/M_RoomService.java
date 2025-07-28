package com.example.AMS.service;

import com.example.AMS.model.Room;
import com.example.AMS.repository.M_RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class M_RoomService {

    @Autowired
    private M_RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(String roomId) {
        Optional<Room> optional = roomRepository.findById(roomId);
        return optional.orElse(null);
    }

    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }
}
