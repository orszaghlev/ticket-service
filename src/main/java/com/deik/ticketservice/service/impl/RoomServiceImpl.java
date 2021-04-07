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
        Optional<Room> roomsWithTheGivenName = this.roomRepository.findByName(name);
        if (roomsWithTheGivenName.isEmpty()) {
            Room roomToCreate = new Room(name, numberOfRows, numberOfCols);
            roomRepository.save(roomToCreate);
        }
    }

    public void updateRoom(String name, int numberOfRows, int numberOfCols) {
        Optional<Room> roomsWithTheGivenName = this.roomRepository.findByName(name);
        if (roomsWithTheGivenName.isPresent()) {
            Room roomToUpdate = roomsWithTheGivenName.get();
            roomToUpdate.setName(name);
            roomToUpdate.setNumberOfRows(numberOfRows);
            roomToUpdate.setNumberOfCols(numberOfCols);
            roomRepository.save(roomToUpdate);
        }
    }

    public void deleteRoom(String name) {
        Optional<Room> roomsWithTheGivenName = this.roomRepository.findByName(name);
        if (roomsWithTheGivenName.isPresent()) {
            Room roomToDelete = roomsWithTheGivenName.get();
            roomRepository.delete(roomToDelete);
        }
    }

    public List<Room> listRooms() {
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> rooms.add(room));
        return rooms;
    }

}
