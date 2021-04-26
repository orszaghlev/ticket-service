package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.service.exception.RoomException;

import java.util.List;

public interface RoomService {

    void createRoom(String name, int numberOfRows, int numberOfCols) throws RoomException;

    void updateRoom(String name, int numberOfRows, int numberOfCols) throws RoomException;

    void deleteRoom(String name) throws RoomException;

    List<Room> listRooms();

}
