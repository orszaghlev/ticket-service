package com.deik.ticketservice.service;

import com.deik.ticketservice.persistence.entity.Room;

import java.util.List;

public interface RoomService {

    void createRoom(String name, int numberOfRows, int numberOfCols);

    void updateRoom(String name, int numberOfRows, int numberOfCols);

    void deleteRoom(String name);

    List<Room> listRooms();

}
