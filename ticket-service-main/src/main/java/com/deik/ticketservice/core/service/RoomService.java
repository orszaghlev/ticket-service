package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.model.RoomDto;

import java.util.List;

public interface RoomService {

    void createRoom(RoomDto roomDto) throws RoomException;

    void updateRoom(RoomDto roomDto) throws RoomException;

    void deleteRoom(String name) throws RoomException;

    List<RoomDto> listRooms();

}
