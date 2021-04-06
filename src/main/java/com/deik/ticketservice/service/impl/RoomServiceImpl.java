package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.repository.RoomRepository;
import com.deik.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public void createRoom(String name, int numberOfRows, int numberOfCols) {
        Room room = new Room(name, numberOfRows, numberOfCols);
        roomRepository.save(room);
    }

    public void updateRoom(String name, int numberOfRows, int numberOfCols) {
        Optional<Room> rooms = this.roomRepository.findByName(name);
        if (rooms.isPresent()) {
            Room updatedRoom = rooms.get();
            updatedRoom.setName(name);
            updatedRoom.setNumberOfRows(numberOfRows);
            updatedRoom.setNumberOfCols(numberOfCols);
            roomRepository.save(updatedRoom);
        }
    }

    public void deleteRoom(String name) {
        Optional<Room> rooms = this.roomRepository.findByName(name);
        if (rooms.isPresent()) {
            Room roomToDelete = rooms.get();
            roomRepository.delete(roomToDelete);
        }
    }

    public List<Room> listRooms() {
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> rooms.add(room));
        return rooms;
    }

}
