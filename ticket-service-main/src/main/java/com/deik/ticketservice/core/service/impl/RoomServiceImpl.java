package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.persistence.repository.RoomRepository;
import com.deik.ticketservice.core.service.RoomService;
import com.deik.ticketservice.core.service.exception.RoomException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(String name, int numberOfRows, int numberOfCols) throws RoomException {
        if (roomRepository.findByName(name).isPresent()) {
            throw new RoomException("Room already exists");
        }
        Room roomToCreate = new Room(null, name, numberOfRows, numberOfCols);
        roomRepository.save(roomToCreate);
    }

    @Override
    public void updateRoom(String name, int numberOfRows, int numberOfCols) throws RoomException {
        if (roomRepository.findByName(name).isEmpty()) {
            throw new RoomException("Room doesn't exist");
        }
        Room roomToUpdate = roomRepository.findByName(name).get();
        roomToUpdate.setName(name);
        roomToUpdate.setNumberOfRows(numberOfRows);
        roomToUpdate.setNumberOfCols(numberOfCols);
        roomRepository.save(roomToUpdate);
    }

    @Override
    public void deleteRoom(String name) throws RoomException {
        if (roomRepository.findByName(name).isEmpty()) {
            throw new RoomException("Room doesn't exist");
        }
        Room roomToDelete = roomRepository.findByName(name).get();
        roomRepository.delete(roomToDelete);
    }

    @Override
    public List<Room> listRooms() {
        List<Room> rooms = new LinkedList<>();
        roomRepository.findAll().forEach(rooms::add);
        return rooms;
    }

}
