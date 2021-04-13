package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.repository.RoomRepository;
import com.deik.ticketservice.service.RoomService;
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
    public Room createRoom(String name, int numberOfRows, int numberOfCols) {
        if (roomRepository.findByName(name).isEmpty()) {
            Room roomToCreate = new Room(name, numberOfRows, numberOfCols);
            roomRepository.save(roomToCreate);
            return roomToCreate;
        }
        return null;
    }

    @Override
    public Room updateRoom(String name, int numberOfRows, int numberOfCols) {
        if (roomRepository.findByName(name).isPresent()) {
            Room roomToUpdate = roomRepository.findByName(name).get();
            roomToUpdate.setName(name);
            roomToUpdate.setNumberOfRows(numberOfRows);
            roomToUpdate.setNumberOfCols(numberOfCols);
            roomRepository.save(roomToUpdate);
            return roomToUpdate;
        }
        return null;
    }

    @Override
    public Room deleteRoom(String name) {
        if (roomRepository.findByName(name).isPresent()) {
            Room roomToDelete = roomRepository.findByName(name).get();
            roomRepository.delete(roomToDelete);
            return roomToDelete;
        }
        return null;
    }

    @Override
    public List<Room> listRooms() {
        List<Room> rooms = new LinkedList<>();
        roomRepository.findAll().forEach(rooms::add);
        return rooms;
    }

}
