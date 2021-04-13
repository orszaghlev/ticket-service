package com.deik.ticketservice.service;

import com.deik.ticketservice.entity.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(String name, int numberOfRows, int numberOfCols);

    Room updateRoom(String name, int numberOfRows, int numberOfCols);

    Room deleteRoom(String name);

    List<Room> listRooms();

}
