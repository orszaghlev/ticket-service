package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.persistence.entity.Room;

import java.util.List;

public interface RoomService {

    void createRoom(String name, int numberOfRows, int numberOfCols);

    void updateRoom(String name, int numberOfRows, int numberOfCols);

    void deleteRoom(String name);

    List<Room> listRooms();

}
