package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.persistence.repository.RoomRepository;
import com.deik.ticketservice.core.service.RoomService;
import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.model.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private static final String ROOM_ALREADY_CREATED_MESSAGE = "Room is already in the repository";
    private static final String ROOM_NOT_FOUND_MESSAGE = "Room not found in the repository";

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(RoomDto roomDto) throws RoomException {
        requireNonNull(roomDto);
        if (roomRepository.findByName(roomDto.getName()).isPresent()) {
            throw new RoomException(ROOM_ALREADY_CREATED_MESSAGE);
        }
        Room roomToCreate = new Room(null, roomDto.getName(), roomDto.getNumberOfRows(), roomDto.getNumberOfCols());
        roomRepository.save(roomToCreate);
    }

    @Override
    public void updateRoom(RoomDto roomDto) throws RoomException {
        requireNonNull(roomDto);
        Room roomToUpdate = getRoomByName(roomDto.getName());
        roomToUpdate.setName(roomDto.getName());
        roomToUpdate.setNumberOfRows(roomDto.getNumberOfRows());
        roomToUpdate.setNumberOfCols(roomDto.getNumberOfCols());
        roomRepository.save(roomToUpdate);
    }

    @Override
    public void deleteRoom(String name) throws RoomException {
        Room roomToDelete = getRoomByName(name);
        roomRepository.delete(roomToDelete);
    }

    @Override
    public List<RoomDto> listRooms() {
        return roomRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private Room getRoomByName(String name) throws RoomException {
        if (roomRepository.findByName(name).isEmpty()) {
            throw new RoomException(ROOM_NOT_FOUND_MESSAGE);
        }
        return roomRepository.findByName(name).get();
    }

    private void requireNonNull(RoomDto roomDto) {
        Objects.requireNonNull(roomDto, "Room cannot be null");
        Objects.requireNonNull(roomDto.getName(), "Room name cannot be null");
    }

    private RoomDto convertEntityToDto(Room room) {
        return new RoomDto.Builder()
                .withName(room.getName())
                .withNumberOfRows(room.getNumberOfRows())
                .withNumberOfCols(room.getNumberOfCols())
                .build();
    }

}
