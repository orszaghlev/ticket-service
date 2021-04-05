package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.repository.RoomRepository;
import com.deik.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public void createRoom(String name, int numberOfRows, int numberOfCols) {
        Room room = new Room(1, name, numberOfRows, numberOfCols);
        roomRepository.save(room);
    }

    public void updateRoom(String name, int numberOfRows, int numberOfCols) {

    }

    public void deleteRoom(String name) {
        Room room = roomRepository.findByName(name).get();
        roomRepository.delete(room);
    }

    public List<Room> listRooms() {
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> rooms.add(room));
        return rooms;
    }

}
