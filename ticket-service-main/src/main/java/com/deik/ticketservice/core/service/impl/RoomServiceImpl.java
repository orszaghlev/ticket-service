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

    private static final String ROOM_ALREADY_CREATED_MESSAGE = "Room already created";
    private static final String ROOM_NOT_FOUND_MESSAGE = "Room not found in the repository";

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(String name, int numberOfRows, int numberOfCols) throws RoomException {
        if (roomRepository.findByName(name).isPresent()) {
            throw new RoomException(ROOM_ALREADY_CREATED_MESSAGE);
        }
        Room roomToCreate = new Room(null, name, numberOfRows, numberOfCols);
        roomRepository.save(roomToCreate);
    }

    @Override
    public void updateRoom(String name, int numberOfRows, int numberOfCols) throws RoomException {
        if (roomRepository.findByName(name).isEmpty()) {
            throw new RoomException(ROOM_NOT_FOUND_MESSAGE);
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
            throw new RoomException(ROOM_NOT_FOUND_MESSAGE);
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
