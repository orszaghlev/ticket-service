package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.persistence.repository.RoomRepository;
import com.deik.ticketservice.core.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(String name, int numberOfRows, int numberOfCols) {
        if (roomRepository.findByName(name).isEmpty()) {
            Room roomToCreate = new Room(1, name, numberOfRows, numberOfCols);
            roomRepository.save(roomToCreate);
        }
    }

    @Override
    public void updateRoom(String name, int numberOfRows, int numberOfCols) {
        if (roomRepository.findByName(name).isPresent()) {
            Room roomToUpdate = roomRepository.findByName(name).get();
            roomToUpdate.setName(name);
            roomToUpdate.setNumberOfRows(numberOfRows);
            roomToUpdate.setNumberOfCols(numberOfCols);
            roomRepository.save(roomToUpdate);
        }
    }

    @Override
    public void deleteRoom(String name) {
        if (roomRepository.findByName(name).isPresent()) {
            Room roomToDelete = roomRepository.findByName(name).get();
            roomRepository.delete(roomToDelete);
        }
    }

    @Override
    public List<Room> listRooms() {
        List<Room> rooms = new LinkedList<>();
        roomRepository.findAll().forEach(rooms::add);
        return rooms;
    }

}
